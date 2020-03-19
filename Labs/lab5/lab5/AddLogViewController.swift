//
//  AddLogViewController.swift
//  lab5
//
//  Created by Nathanael Bennett on 3/18/20.
//  Copyright © 2020 Nathanael Bennett. All rights reserved.
//

import UIKit

class AddLogViewController: UIViewController {

    var date: Date?
    var length: Double?
    var relaxation = "Refreshed"
    
    @IBOutlet weak var datePicker: UIDatePicker!
    @IBOutlet weak var hoursLabel: UILabel!
    @IBOutlet weak var hoursStepper: UIStepper!
    @IBOutlet weak var segmentControl: UISegmentedControl!
    
    @IBAction func stepperChanged(_ sender: Any) {
        length = hoursStepper.value
        if hoursStepper.value == 1.0 {
            hoursLabel.text = "\(hoursStepper.value) hour"
        } else {
            hoursLabel.text = "\(hoursStepper.value) hours"
        }
    }
    
    @IBAction func segmentChanged(_ sender: Any) {
        switch segmentControl.selectedSegmentIndex {
        case 0:
            relaxation = "Refreshed"
        case 1:
            relaxation = "Okay"
        case 2:
            relaxation = "Tired"
        default:
            relaxation = "Refreshed"
        }
        print("Tired level is: \(relaxation)")
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "save" {
            date = datePicker.date
            length = hoursStepper.value
        }
    }

}
