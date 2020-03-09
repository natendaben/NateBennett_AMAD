//
//  MountainListTableViewController.swift
//  checklist
//
//  Created by Nathanael Bennett on 3/5/20.
//  Copyright © 2020 Nathanael Bennett. All rights reserved.
//

//Mountain List Viewing Screen

import UIKit

class MountainListTableViewController: UITableViewController {

    //variables
    var mountainList = [String]()
    var mountainDataController = MountainDataController()
    var selectedMountainRange = 0
    var mountainRange = ""
    var mountainArray = [Mountain]()

    
    override func viewWillAppear(_ animated: Bool) {
        
        //load in data
        mountainList = mountainDataController.getMountainListForRange(index: selectedMountainRange)
        let rangeList = mountainDataController.getMountainRangeList()
        mountainRange = rangeList[selectedMountainRange]
        mountainArray = mountainDataController.getMountainsInRange(index: selectedMountainRange)
        
        //reload table view
        tableView.reloadData()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }

    // MARK: - Table view data source
    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return mountainList.count
    }

    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "MountainCell", for: indexPath)

        //get mountain object
        let mountain = mountainArray[indexPath.row]
        
        //set icon based on whether mountain has been climbed or not
        if mountain.ticks == 0 {
            cell.imageView?.image = UIImage(named: "blank")
        } else {
            cell.imageView?.image = UIImage(named: "check")
        }
        
        //set cell text
        cell.textLabel?.text = mountainList[indexPath.row]

        return cell
    }

    // MARK: - Navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "SelectMountainSegue" {
            
            //get destination
            let dest = segue.destination as! DetailViewController
            
            //get index path
            let indexPath = tableView.indexPath(for: sender as! UITableViewCell)
            if let selectedMountain = indexPath?.row {
                
                //send info to detail screen
                dest.rangeName = mountainRange
                dest.mountainDataController = mountainDataController
                dest.mountainIndex = selectedMountain
                dest.mountainRangeIndex = selectedMountainRange
            }
        }
    }
    

}
