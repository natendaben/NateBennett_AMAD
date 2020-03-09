//
//  DetailViewController.swift
//  checklist
//
//  Created by Nathanael Bennett on 3/5/20.
//  Copyright © 2020 Nathanael Bennett. All rights reserved.
//

//View Mountain Details Screen

import UIKit

class DetailViewController: UIViewController {

    //variables
    var mountain: Mountain?
    var rangeName = String()
    var mountainDataController = MountainDataController()
    var mountainRangeIndex = 0
    var mountainIndex = 0
    
    //outlets
    @IBOutlet weak var peakImage: UIImageView!
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var rangeLabel: UILabel!
    @IBOutlet weak var elevationLabel: UILabel!
    @IBOutlet weak var notesView: UITextView!
    @IBOutlet weak var classLabel: UILabel!
    @IBOutlet weak var rankLabel: UILabel!
    @IBOutlet weak var ticksLabel: UILabel!
    
    //setup view
    override func viewWillAppear(_ animated: Bool) {
        //get selected mountain
        mountain = mountainDataController.getMountain(rangeIndex: mountainRangeIndex, mountainIndex: mountainIndex)

        //update labels and views
        nameLabel.text = mountain?.name
        elevationLabel.text = mountain?.elevation
        notesView.text = mountain?.notes
        rangeLabel.text = rangeName
        classLabel.text = "Class \(mountain?.peakClass ?? 1)"
        
        //round corners of class label
        classLabel.layer.masksToBounds = true
        classLabel.layer.cornerRadius = 5
        
        //set text for ticks
        if mountain?.ticks ?? 0 == 1 {
            ticksLabel.text = "Climbed \(mountain?.ticks ?? 1) time"
        } else {
            ticksLabel.text = "Climbed \(mountain?.ticks ?? 0) times"
        }
        
        //set rank label
        rankLabel.text = "\(mountain?.rank ?? 0)"
        
        //set the peak image
        peakImage.image = UIImage(named: mountain!.imageName)
        
        //set color for class label based on class
        var classColor = UIColor()
        switch mountain?.peakClass {
        case 1:
            classColor = UIColor(named: "C1")!
            print("C1")
        case 2:
            classColor = UIColor(named: "C2")!
            print("C2")
        case 3:
            classColor = UIColor(named: "C3")!
            print("C3")
        case 4:
            classColor = UIColor(named: "C4")!
            print("C4")
        case 5:
            classColor = UIColor(named: "C5")!
            print("C5")
        default:
            classColor = UIColor(named: "C1")!
        }
        classLabel.layer.backgroundColor = classColor.cgColor
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        //make background color of notes view grey
        let grey = UIColor(red: 100.0/255.0, green: 100.0/255.0, blue: 100.0/255.0, alpha: 0.1)
        notesView.layer.masksToBounds = true
        notesView.layer.cornerRadius = 5
        notesView.backgroundColor = grey
        
        //enable large titles
        navigationController?.navigationBar.prefersLargeTitles = true
    }
    
    //fumction for unwinding segue from edit view controller
    @IBAction func unwindSegue(_ segue: UIStoryboardSegue){
        if segue.identifier == "save" {
            
            //get source
            let source = segue.source as! EditMountainViewController
            
            //call update mountain function with info from source
            mountainDataController.updateMountainInfo(notes: source.notes, ticks: source.ticks, mountainIndex: mountainIndex, rangeIndex: mountainRangeIndex)
            
            //update local view with new ticks and notes info
            if source.ticks == 1 {
                ticksLabel.text = "Climbed \(source.ticks) time"
            } else {
                ticksLabel.text = "Climbed \(source.ticks) times"
            }
            notesView.text = source.notes
        }
    }
    
    // MARK: - Navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "editMountain" {
            
            //get destination
            let dest = segue.destination as! EditMountainViewController
            
            //send mountain info to next screen
            dest.mountain = mountainDataController.getMountain(rangeIndex: mountainRangeIndex, mountainIndex: mountainIndex)
            dest.rangeName = rangeName
        }
    }

}
