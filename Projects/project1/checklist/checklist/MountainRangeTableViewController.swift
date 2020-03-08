//
//  MountainRangeTableViewController.swift
//  checklist
//
//  Created by Nathanael Bennett on 3/5/20.
//  Copyright © 2020 Nathanael Bennett. All rights reserved.
//

import UIKit

class MountainRangeTableViewController: UITableViewController {
    
    var mountainRangeList = [String]()
    let mountainDataController = MountainDataController()

    override func viewDidLoad() {
        super.viewDidLoad()
        
        // load mountain range data
        do {
            try mountainDataController.loadData()
            mountainRangeList = mountainDataController.getMountainRangeList()
        } catch {
            print("Problem loading data")
            print(error)
        }
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

        cell.textLabel?.text = mountainRangeList[indexPath.row]

        return cell
    }

    /*
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

    /*
    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "SelectMountainRangeSegue" {
            let dest = segue.destination as! MountainListTableViewController
            let indexPath = tableView.indexPath(for: sender as! UITableViewCell)
            if let selectedRange = indexPath?.row {
                dest.title = mountainRangeList[selectedRange]
                dest.selectedMountainRange = selectedRange
                dest.mountainDataController = mountainDataController
            }
        } else if segue.identifier == "RangeInfoSegue" {
            let dest = segue.destination as! RangeInfoTableViewController
            let indexPath = tableView.indexPath(for: sender as! UITableViewCell)
            if let selectedRange = indexPath?.row {
                let range = mountainDataController.getMountainRange(rangeIndex: selectedRange)
                let totalNumberOfMountains = Float(range.totalNumber)!
                let mountainList = mountainDataController.getMountainsInRange(index: selectedRange)
                var climbedMountains = [Mountain]()
                for mountain in mountainList { //check how many mountains have been climbed
                    if mountain.ticks > 0 {
                        climbedMountains.append(mountain)
                    }
                }
                let numberOfMountainsClimbed = Float(climbedMountains.count)
                let percentageCompleteFloat: Float
                if totalNumberOfMountains > 0 {
                    percentageCompleteFloat = numberOfMountainsClimbed/totalNumberOfMountains * 100
                    
                } else {
                    print("Number of mountains in range is 0")
                    percentageCompleteFloat = 0.0
                }
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
