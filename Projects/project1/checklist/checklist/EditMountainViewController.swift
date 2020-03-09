//
//  EditMountainViewController.swift
//  checklist
//
//  Created by Nathanael Bennett on 3/6/20.
//  Copyright © 2020 Nathanael Bennett. All rights reserved.
//

//Edit Mountain Screen

import UIKit

class EditMountainViewController: UIViewController, UITextViewDelegate {

    //variables
    var ticks = 0
    var notes = ""
    var mountain: Mountain?
    var rangeName = String()
    
    //outlets
    @IBOutlet weak var peakImage: UIImageView!
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var rangeLabel: UILabel!
    @IBOutlet weak var ticksLabel: UILabel!
    @IBOutlet weak var stepper: UIStepper!
    @IBOutlet weak var notesView: UITextView!
    
    //function to update ticks based on stepper
    @IBAction func updateTicks(_ sender: Any) {
        ticksLabel.text = String(format: "%.0f", stepper.value)
        ticks = Int(stepper.value)
    }
    
    //load data when view appears
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
        
        //curve borders of notes, give grey border
        notesView.layer.masksToBounds = true
        notesView.layer.cornerRadius = 5
        let grey = UIColor(red: 100.0/255.0, green: 100.0/255.0, blue: 100.0/255.0, alpha: 0.1)
        notesView.layer.borderColor = grey.cgColor
        notesView.layer.borderWidth = CGFloat(2.0)
        
        //handle dismissing the keyboard
        notesView.delegate = self
        let tap: UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(self.dismissKeyboard)) //allows view to detect tap gestures and call dismissKeyboard function upon tap
        view.addGestureRecognizer(tap)
        
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardWillShow), name: UIResponder.keyboardWillShowNotification, object: nil) //defines view as observer to receive the keyboardWillShowNotification, will then call function keyboardWillShow
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardWillHide), name: UIResponder.keyboardWillHideNotification, object: nil) //defines view as observer to receive the keyboardWillHideNotification, will then call function keyboardWillHide
    }
    
    //function for defining size of keyboard view to slide view up for keyboard
    @objc func keyboardWillShow(notification: NSNotification){
         if let keyboardSize = (notification.userInfo?[UIResponder.keyboardFrameEndUserInfoKey] as? NSValue)?.cgRectValue {
             if self.view.frame.origin.y == 0 {
                 self.view.frame.origin.y -= keyboardSize.height
             }
         }
     }
    
    //function for checking if keyboard is visible and moving view down if so
     @objc func keyboardWillHide(notification: NSNotification){
         if((notification.userInfo?[UIResponder.keyboardFrameEndUserInfoKey] as? NSValue)?.cgRectValue) != nil {
             if self.view.frame.origin.y != 0 {
                 self.view.frame.origin.y = 0
             }
         }
     }
    
    //function for dismissing keyboard
    @objc func dismissKeyboard(){
        view.endEditing(true)
    }

    // MARK: - Navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "save" {
            //update variables for updating mountain object
            notes = notesView.text
            ticks = Int(stepper.value)
        }
    }
}
