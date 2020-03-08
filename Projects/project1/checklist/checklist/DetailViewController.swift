//
//  DetailViewController.swift
//  checklist
//
//  Created by Nathanael Bennett on 3/5/20.
//  Copyright © 2020 Nathanael Bennett. All rights reserved.
//

import UIKit

class DetailViewController: UIViewController {

    var mountain: Mountain?
    var rangeName = String()
    var mountainDataController = MountainDataController()
    var mountainRangeIndex = 0
    var mountainIndex = 0
    
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var rangeLabel: UILabel!
    @IBOutlet weak var elevationLabel: UILabel!
    @IBOutlet weak var notesView: UITextView!
    @IBOutlet weak var classLabel: UILabel!
    @IBOutlet weak var rankLabel: UILabel!
    @IBOutlet weak var ticksLabel: UILabel!
    
    override func viewWillAppear(_ animated: Bool) {
        mountain = mountainDataController.getMountain(rangeIndex: mountainRangeIndex, mountainIndex: mountainIndex)
        print("Mountain notes: \(mountain?.notes)")
        print("Mountain ticks: \(mountain?.ticks)")
        nameLabel.text = mountain?.name
        elevationLabel.text = mountain?.elevation
        notesView.text = mountain?.notes
        rangeLabel.text = rangeName
        classLabel.text = "Class \(mountain?.peakClass ?? 1)"
        classLabel.layer.masksToBounds = true
        classLabel.layer.cornerRadius = 5
        if mountain?.ticks ?? 0 == 1 {
            ticksLabel.text = "Climbed \(mountain?.ticks ?? 1) time"
        } else {
            ticksLabel.text = "Climbed \(mountain?.ticks ?? 0) times"
        }
        rankLabel.text = "\(mountain?.rank ?? 0)"
        
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }
    
    @IBAction func unwindSegue(_ segue: UIStoryboardSegue){
        if segue.identifier == "save" {
            let source = segue.source as! EditMountainViewController
            mountainDataController.updateMountainInfo(notes: source.notes, ticks: source.ticks, mountainIndex: mountainIndex, rangeIndex: mountainRangeIndex)
            if source.ticks == 1 {
                ticksLabel.text = "Climbed \(source.ticks) time"
            } else {
                ticksLabel.text = "Climbed \(source.ticks) times"
            }
            notesView.text = source.notes
        }
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
