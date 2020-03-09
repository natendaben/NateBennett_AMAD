

//
//  RangeInfoTableViewController.swift
//  checklist
//
//  Created by Nathanael Bennett on 3/6/20.
//  Copyright © 2020 Nathanael Bennett. All rights reserved.
//

//View Range Information Screen

import UIKit

class RangeInfoTableViewController: UITableViewController {
    
    //outlets
    @IBOutlet weak var totalNumberLabel: UILabel!
    @IBOutlet weak var numberClimbedLabel: UILabel!
    @IBOutlet weak var percentCompleteLabel: UILabel!
    
    //variables
    var rangeName = String()
    var totalMountains = String()
    var mountainsClimbed = String()
    var percentComplete = String()
    
    override func viewWillAppear(_ animated: Bool) {
        totalNumberLabel.text = totalMountains
        percentCompleteLabel.text = mountainsClimbed
        percentCompleteLabel.text = percentComplete
        numberClimbedLabel.text = mountainsClimbed
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
}
