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
    var mountainArray = [Mountain]()

    override func viewWillAppear(_ animated: Bool) {
        mountainList = mountainDataController.getMountainListForRange(index: selectedMountainRange)
        let rangeList = mountainDataController.getMountainRangeList()
        mountainRange = rangeList[selectedMountainRange]
        mountainArray = mountainDataController.getMountainsInRange(index: selectedMountainRange)
        tableView.reloadData()
        print("Table view reloaded")
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

        let mountain = mountainArray[indexPath.row]
        if mountain.ticks == 0 {
            cell.imageView?.image = UIImage(named: "blank")
        } else {
            cell.imageView?.image = UIImage(named: "check")
        }
        cell.textLabel?.text = mountainList[indexPath.row]
        print("\(mountain.name) has \(mountain.ticks) ticks")

        return cell
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
