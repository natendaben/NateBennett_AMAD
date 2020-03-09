//
//  MountainRangeTableViewController.swift
//  checklist
//
//  Created by Nathanael Bennett on 3/5/20.
//  Copyright © 2020 Nathanael Bennett. All rights reserved.
//

//View Mountain Ranges Screen

import UIKit

class MountainRangeTableViewController: UITableViewController {
    
    //variables
    var mountainRangeList = [String]()
    let mountainDataController = MountainDataController()

    override func viewDidLoad() {
        super.viewDidLoad()
        
        //load mountain range data
        do {
            try mountainDataController.loadData()
            mountainRangeList = mountainDataController.getMountainRangeList()
        } catch {
            print("Problem loading data")
            print(error)
        }
        
        //enable large titles
        navigationController?.navigationBar.prefersLargeTitles = true

    }

    // MARK: - Table view data source
    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return mountainRangeList.count
    }

    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "MountainRangeCell", for: indexPath)

        //set text for mountain range name
        cell.textLabel?.text = mountainRangeList[indexPath.row]

        return cell
    }

    // MARK: - Navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "SelectMountainRangeSegue" {
            
            //get destination
            let dest = segue.destination as! MountainListTableViewController
            
            //get index path
            let indexPath = tableView.indexPath(for: sender as! UITableViewCell)
            if let selectedRange = indexPath?.row {
                
                //send info to mountain range viewing screen
                dest.title = mountainRangeList[selectedRange]
                dest.selectedMountainRange = selectedRange
                dest.mountainDataController = mountainDataController
            }
        } else if segue.identifier == "RangeInfoSegue" {
            
            //get destination
            let dest = segue.destination as! RangeInfoTableViewController
            
            //get index path
            let indexPath = tableView.indexPath(for: sender as! UITableViewCell)
            if let selectedRange = indexPath?.row {
                
                //do some calculations to figure out total number of mountains, mountains climbed, and percentage of mountains climbed in that range...
                //get range
                let range = mountainDataController.getMountainRange(rangeIndex: selectedRange)
                //get total number of mountains in range
                let totalNumberOfMountains = Float(range.totalNumber)!
                //get mountain list for that range
                let mountainList = mountainDataController.getMountainsInRange(index: selectedRange)
                //set up new empty array for climbed mountains
                var climbedMountains = [Mountain]()
                //check how many mountains in range have been climbed
                for mountain in mountainList {
                    if mountain.ticks > 0 { //if mountain has been climbed
                        climbedMountains.append(mountain) //add it to climbed mountains array
                    }
                }
                //get climbed mountains count
                let numberOfMountainsClimbed = Float(climbedMountains.count)
                //set up percentage variable
                let percentageCompleteFloat: Float
                //check to make sure we aren't dividing by 0
                if totalNumberOfMountains > 0 {
                    percentageCompleteFloat = numberOfMountainsClimbed/totalNumberOfMountains * 100
                } else {
                    print("Number of mountains in range is 0")
                    percentageCompleteFloat = 0.0
                }
                //format string for percent of range climbed
                let percentLabelValue = String(format: "%.0f", percentageCompleteFloat) + "%"
                
                //send data to range info view controller
                dest.title = "\(mountainRangeList[selectedRange]) Stats"
                dest.percentComplete = percentLabelValue
                dest.totalMountains = String(format: "%.0f", totalNumberOfMountains)
                dest.mountainsClimbed = String(climbedMountains.count)
            }
        }
    }

}
