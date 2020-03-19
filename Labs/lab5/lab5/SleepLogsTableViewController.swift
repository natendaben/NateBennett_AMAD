//
//  SleepLogsTableViewController.swift
//  lab5
//
//  Created by Nathanael Bennett on 3/18/20.
//  Copyright © 2020 Nathanael Bennett. All rights reserved.
//

import UIKit

class SleepLogsTableViewController: UITableViewController {

    //instantiate data controller
    var sleepDC = LogDataController()
    
    //local copy of data
    var sleepData = [SleepLog]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        //set data update listener
        sleepDC.onDataUpdate = {[weak self] (data: [SleepLog]) -> Void in self?.newData(data: data)}
        
        //load data
        sleepDC.loadData()
    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return sleepData.count
    }

    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "SleepCell", for: indexPath)

        //get sleepLog object
        let log = sleepData[indexPath.row]
        
        //set the labels
        cell.textLabel?.text = log.getDate()
        cell.detailTextLabel?.text = "\(log.length) hours"

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
            // Delete the row from the data source
            let id = sleepData[indexPath.row].id
            sleepDC.deleteLog(logID: id)
            print("log = \(id)")
            //tableView.deleteRows(at: [indexPath], with: .fade)
        }
    }

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

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */
    @IBAction func unwindSegue(segue: UIStoryboardSegue){
        if segue.identifier == "save" {
            let source = segue.source as! AddLogViewController
            sleepDC.writeData(date: source.date!, length: source.length!, relaxation: source.relaxation)
        }
    }
    
    func newData(data: [SleepLog]) {
        //set the sleep log
        sleepData = data
        //reload tableView
        tableView.reloadData()
    }

}
