//
//  EditMountainViewController.swift
//  checklist
//
//  Created by Nathanael Bennett on 3/6/20.
//  Copyright © 2020 Nathanael Bennett. All rights reserved.
//

import UIKit

class EditMountainViewController: UIViewController, UITextViewDelegate {

    var ticks = 0
    var notes = ""
    var mountain: Mountain?
    var rangeName = String()
    
    func textViewShouldEndEditing(_ textView: UITextView) -> Bool {
        return true
    }

    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        view.endEditing(true)
        super.touchesBegan(touches, with: event)
    }
    
    @IBOutlet weak var peakImage: UIImageView!
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var rangeLabel: UILabel!
    @IBOutlet weak var ticksLabel: UILabel!
    @IBOutlet weak var stepper: UIStepper!
    @IBOutlet weak var notesView: UITextView!
    
    @IBAction func updateTicks(_ sender: Any) {
        ticksLabel.text = String(format: "%.0f", stepper.value)
        ticks = Int(stepper.value)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        stepper.value = Double(mountain?.ticks ?? 0)
        notesView.text = mountain?.notes
        ticksLabel.text = String(format: "%.0f", stepper.value)
        peakImage.image = UIImage(named: mountain!.imageName)
        nameLabel.text = mountain?.name
        rangeLabel.text = rangeName
        
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        notesView.layer.masksToBounds = true
        notesView.layer.cornerRadius = 5
        let grey = UIColor(red: 100.0/255.0, green: 100.0/255.0, blue: 100.0/255.0, alpha: 0.1)
//        notesView.backgroundColor = grey
        notesView.layer.borderColor = grey.cgColor
        notesView.layer.borderWidth = CGFloat(2.0)
        notesView.delegate = self
        let tap: UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(self.dismissKeyboard)) //allows view to detect tap gestures and call dismissKeyboard function upon tap
        view.addGestureRecognizer(tap)
        
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardWillShow), name: UIResponder.keyboardWillShowNotification, object: nil) //defines view as observer to receive the keyboardWillShowNotification, will then call function keyboardWillShow
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardWillHide), name: UIResponder.keyboardWillHideNotification, object: nil) //defines view as observer to receive the keyboardWillHideNotification, will then call function keyboardWillHide
    }
    @objc func keyboardWillShow(notification: NSNotification){
         if let keyboardSize = (notification.userInfo?[UIResponder.keyboardFrameEndUserInfoKey] as? NSValue)?.cgRectValue {
             if self.view.frame.origin.y == 0 {
                 self.view.frame.origin.y -= keyboardSize.height
             }
         }
     }//This function defines the size of the keyboard view based on the size of the iOS screen, then uses height of the keyboard to figure out how far to slide the view and UI objects up to make room for virtual keyboard
    
     @objc func keyboardWillHide(notification: NSNotification){
         if((notification.userInfo?[UIResponder.keyboardFrameEndUserInfoKey] as? NSValue)?.cgRectValue) != nil {
             if self.view.frame.origin.y != 0 {
                 self.view.frame.origin.y = 0
             }
         }
     }//This function checks if the virtual keyboard is visible. If so, it moves the view back down to cover the keyboard. Otherwise it does nothing
    @objc func dismissKeyboard(){
        view.endEditing(true) //this sends the keyboardWillHideNotification to the view, and runs whenever the user taps somewhere outside of the text field
    }

    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "save" {
            notes = notesView.text
            ticks = Int(stepper.value)
            print("Notes: \(notes)")
            print("Ticks: \(ticks)")
        }
    }
}
