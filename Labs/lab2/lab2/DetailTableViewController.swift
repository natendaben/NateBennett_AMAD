//
//  DetailTableViewController.swift
//  lab2
//
//  Created by Nathanael Bennett on 2/15/20.
//  Copyright © 2020 Nathanael Bennett. All rights reserved.
//

import UIKit

class DetailTableViewController: UITableViewController {
    var mountainRangeController = MountainRangeController()
    var mountainList = [String]()
    var selectedRange = 0

    var searchController = UISearchController()
    let resultsController = SearchResultsController()
    
    override func viewWillAppear(_ animated: Bool) {
        //extendedLayoutIncludesOpaqueBars = true
        navigationItem.largeTitleDisplayMode = .never

        mountainList = mountainRangeController.getMountains(index: selectedRange)
        
        resultsController.allMountains = mountainList
        searchController = UISearchController(searchResultsController: resultsController)
        
        searchController.searchBar.placeholder = "Filter"
        searchController.searchBar.sizeToFit()
        
        tableView.tableHeaderView = searchController.searchBar
        searchController.searchResultsUpdater = resultsController
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
        cell.textLabel?.text = mountainList[indexPath.row]
        return cell
    }

    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }

    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            //notify data model
            mountainList.remove(at: indexPath.row)
            //notify our instance
            mountainRangeController.removeMountain(rangeIndex: selectedRange, mountainIndex: indexPath.row)
            // Delete the row from the data source
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }

    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {
        //get indices of our rows
        let fromRowIndex = fromIndexPath.row
        let toRowIndex = to.row

        //get name of mountain being moved
        let mountainToMove = mountainList[fromRowIndex]
        
        //swap mountains in array
        mountainList.swapAt(fromRowIndex, toRowIndex)
        
        //swap in our data controller to keep everything up-to-date
        mountainRangeController.removeMountain(rangeIndex: selectedRange, mountainIndex: fromRowIndex)
        mountainRangeController.addMountain(rangeIndex: selectedRange, mountain: mountainToMove, mountainIndex: toRowIndex)
        
    }

    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }

    @IBAction func unwindSegue(_ segue: UIStoryboardSegue){
        if segue.identifier == "save" {
            let source = segue.source as! AddMountainViewController
            if source.mountainName.isEmpty == false {
                //update data model
                mountainRangeController.addMountain(rangeIndex: selectedRange, mountain: source.mountainName, mountainIndex: mountainList.count)
                //upate local copy
                mountainList.append(source.mountainName)
                //refresh table
                tableView.reloadData()
                //refresh search controller
                resultsController.allMountains = mountainList
            }
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
