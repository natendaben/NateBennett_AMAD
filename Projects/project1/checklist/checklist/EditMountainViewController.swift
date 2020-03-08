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
    var mountain: Mountain?
    var rangeName = String()
    
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
        notesView.backgroundColor = grey
        
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
