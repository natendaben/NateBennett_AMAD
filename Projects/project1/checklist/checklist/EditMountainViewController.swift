//
//  EditMountainViewController.swift
//  checklist
//
//  Created by Nathanael Bennett on 3/6/20.
//  Copyright © 2020 Nathanael Bennett. All rights reserved.
//

import UIKit

class EditMountainViewController: UIViewController {

    var ticks = 0
    var notes = ""
    
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var rangeLabel: UILabel!
    @IBOutlet weak var ticksLabel: UILabel!
    @IBOutlet weak var stepper: UIStepper!
    @IBOutlet weak var notesView: UITextView!
    
    @IBAction func updateTicks(_ sender: Any) {
        ticksLabel.text = String(format: "%.0f", stepper.value)
        ticks = Int(stepper.value)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        notesView.layer.masksToBounds = true
        notesView.layer.cornerRadius = 5
        let grey = UIColor(red: 100.0/255.0, green: 100.0/255.0, blue: 100.0/255.0, alpha: 0.1)
        notesView.backgroundColor = grey
    }
    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "save" {
            notes = notesView.text
            print("Notes: \(notes)")
            print("Ticks: \(ticks)")
        }
    }
}
