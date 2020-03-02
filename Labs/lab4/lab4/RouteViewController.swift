//
//  RouteViewController.swift
//  lab4
//
//  Created by Nathanael Bennett on 3/1/20.
//  Copyright © 2020 Nathanael Bennett. All rights reserved.
//

import UIKit

class RouteViewController: UIViewController {

    //Variables
    var routeName = String()
    var grade = String()
    var pitches = Int()
    var type = String()
    var rating = String()
    
    //Connections
    @IBOutlet weak var routeNameLabel: UILabel!
    @IBOutlet weak var gradeLabel: UILabel!
    @IBOutlet weak var pitchLabel: UILabel!
    @IBOutlet weak var typeLabel: UILabel!
    @IBOutlet weak var ratingLabel: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        routeNameLabel.text = routeName
        gradeLabel.text = grade
        if pitches == 1 {
            pitchLabel.text = "1 pitch"
        } else {
            let pitchNumber = String(pitches)
            pitchLabel.text = "\(pitchNumber) pitches"
        }
        typeLabel.text = type
        ratingLabel.text = "\(rating)/5 stars"
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
