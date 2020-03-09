//
//  MountainListTableViewController.swift
//  checklist
//
//  Created by Nathanael Bennett on 3/5/20.
//  Copyright © 2020 Nathanael Bennett. All rights reserved.
//

import UIKit

class MountainListTableViewController: UITableViewController {

    var mountainList = [String]()
    var mountainDataController = MountainDataController()
    var selectedMountainRange = 0
    var mountainRange = ""
    
    override func viewWillAppear(_ animated: Bool) {
        mountainList = mountainDataController.getMountainListForRange(index: selectedMountainRange)
        let rangeList = mountainDataController.getMountainRangeList()
        mountainRange = rangeList[selectedMountainRange]
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem
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

        cell.textLabel?.text = mountainList[indexPath.row]

        return cell
    }

    /*
    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {

    }
    */

    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }

    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "SelectMountainSegue" {
            let dest = segue.destination as! DetailViewController
            let indexPath = tableView.indexPath(for: sender as! UITableViewCell)
            if let selectedMountain = indexPath?.row {
//                dest.title = mountainList[selectedMountain]
                dest.rangeName = mountainRange
                dest.mountainDataController = mountainDataController
                dest.mountainIndex = selectedMountain
                dest.mountainRangeIndex = selectedMountainRange
            }
        }
    }

}
