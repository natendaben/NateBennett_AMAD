package com.example.lab7.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.lab7.R
import com.example.lab7.database.Book

class MainFragment : Fragment(), BookRecyclerAdapter.BookItemListener {

    private lateinit var bookRecyclerView: RecyclerView
    private lateinit var adapter: BookRecyclerAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var navController: NavController


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        (activity as AppCompatActivity?)?.supportActionBar?.title = "Book Reviews"

        val root = inflater.inflate(R.layout.main_fragment, container, false)

        bookRecyclerView = root.findViewById(R.id.bookRecyclerView)
        adapter = BookRecyclerAdapter(requireContext(), emptyList(), this)
        bookRecyclerView.adapter = adapter

        viewModel.bookList.observe(viewLifecycleOwner, Observer {
            adapter.bookList = it
            adapter.notifyDataSetChanged()
        })
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.addBook){
            navController.navigate(R.id.action_mainFragment_to_addBookFragment)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBookItemClick(book: Book) {
        viewModel.currentBook.value = book
        navController.navigate(R.id.action_mainFragment_to_bookDetailsFragment)
    }
}
