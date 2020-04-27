package com.example.lab7.ui.main

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.lab7.R
import com.example.lab7.database.Book
import com.example.lab7.hideKeyboard

class BookDetailsFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var navController: NavController


    private lateinit var titleTextInput: EditText
    private lateinit var pagesTextInput: EditText
    private lateinit var ratingRadioGroup: RadioGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        (activity as AppCompatActivity?)?.supportActionBar?.title = "Review New Book"

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)


        val root = inflater.inflate(R.layout.fragment_book_details, container, false)

        titleTextInput = root.findViewById(R.id.titleTextInput)
        pagesTextInput = root.findViewById(R.id.pagesTextInput)
        ratingRadioGroup = root.findViewById(R.id.starsRadioGroup)

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.saveBook){
            if(titleTextInput.text != null && pagesTextInput != null && ratingRadioGroup.checkedRadioButtonId != -1){
                val bookTitle = titleTextInput.text
                val bookPages = pagesTextInput.text
                val starsId = ratingRadioGroup.checkedRadioButtonId
                var stars: Int = 0

                when(starsId){
                    R.id.one -> stars = 1
                    R.id.two -> stars = 2
                    R.id.three -> stars = 3
                    R.id.four -> stars = 4
                    R.id.five -> stars = 5
                }

                Log.i("books_logging", "You've added ${bookTitle} which is ${bookPages} pages long and you rated it ${stars} stars")
                viewModel.addBook(Book(title = bookTitle.toString(), rating = stars, pages = Integer.parseInt(bookPages.toString())))
                hideKeyboard()
                navController.navigateUp()
                val text = "Book review added successfully!"
                val toast = Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT)
                toast.show()
            } else {
                val text = "Please make sure you've entered all the information for your review!"
                val toast = Toast.makeText(requireContext(), text, Toast.LENGTH_LONG)
                toast.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
