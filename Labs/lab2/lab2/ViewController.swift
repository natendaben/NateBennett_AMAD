//
//  ViewController.swift
//  lab2
//
//  Created by Nathanael Bennett on 2/15/20.
//  Copyright © 2020 Nathanael Bennett. All rights reserved.
//

import UIKit

class ViewController: UITableViewController {
    var mountainRangeList = [String]()
    let mountainRangeController = MountainRangeController()

    override func viewDidLoad() {
        super.viewDidLoad()
        //try to load our mountain range list
        do {
            try mountainRangeController.loadData()
            mountainRangeList = mountainRangeController.getMountainRanges()
        } catch {
            print(error)
        }
        
        navigationController?.navigationBar.prefersLargeTitles = true //enable large title
        
        //get app instance
        let app = UIApplication.shared
        
        //subscribe to willResignActive notifications
        NotificationCenter.default.addObserver(self, selector: #selector(ViewController.applicationWillResignActive(_:)), name: UIApplication.willResignActiveNotification, object: app)
    }
    
    //called automatically when UIApplicationWillResignActive notification is posted for our app, needs to take NSNotification as a parameter
    @objc func applicationWillResignActive(_ notification: NSNotification){
        do {
            try mountainRangeController.writeData()
        } catch {
            print(error)
        }
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return mountainRangeList.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let mountainRangeCell = tableView.dequeueReusableCell(withIdentifier: "RangeCell", for: indexPath)
        mountainRangeCell.textLabel?.text = mountainRangeList[indexPath.row]
        return mountainRangeCell
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "mountainSegue" {
            //get our next view controller
            let nextViewController = segue.destination as! DetailTableViewController
            //get cell that triggered transition
            let indexPath = tableView.indexPath(for: sender as! UITableViewCell)
            //set data in next destination controller
            if let selectedRange = indexPath?.row {
                nextViewController.title = mountainRangeList[selectedRange]
                nextViewController.selectedRange = selectedRange
                nextViewController.mountainRangeController = mountainRangeController
            }
        } else if segue.identifier == "rangeSegue" {
            //get destination
            let rangeInfoViewController = segue.destination as! RangeInfoTableViewController
            
            //get data
            let indexPath = tableView.indexPath(for: sender as! UITableViewCell)
            let totalNumberOfMountains = mountainRangeController.getTotalMountainsInRange(index: indexPath!.row)
            let mountainList = mountainRangeController.getMountains(index: indexPath!.row)
            let numberMountainsClimbed = Float(mountainList.count)
                
            //calculate percentage complete for range
            let totalMountains = Float(totalNumberOfMountains)!
            let percentageCompleteFloat: Float
            percentageCompleteFloat = numberMountainsClimbed/totalMountains*100
            let percentLabelValue = String(format: "%.0f", percentageCompleteFloat) + "%"
            
            //send info to range info view controller
            rangeInfoViewController.percentComplete = percentLabelValue
            rangeInfoViewController.totalMountains = totalNumberOfMountains
            rangeInfoViewController.rangeName = mountainRangeList[indexPath!.row]
            rangeInfoViewController.mountainsClimbed = String(mountainList.count)
            rangeInfoViewController.title = mountainRangeList[indexPath!.row]
        }
    }
    


}

