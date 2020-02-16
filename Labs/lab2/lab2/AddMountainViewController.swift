//
//  AddMountainViewController.swift
//  lab2
//
//  Created by Nathanael Bennett on 2/16/20.
//  Copyright © 2020 Nathanael Bennett. All rights reserved.
//

import UIKit

class AddMountainViewController: UIViewController {

    @IBOutlet weak var addMountainField: UITextField!
    
    var mountainName = String()
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "save"{
            if addMountainField.text?.isEmpty == false {
                mountainName = addMountainField.text!
            }
        }
    }

}
