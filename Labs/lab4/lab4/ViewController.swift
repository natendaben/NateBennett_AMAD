//
//  ViewController.swift
//  lab4
//
//  Created by Nathanael Bennett on 2/28/20.
//  Copyright © 2020 Nathanael Bennett. All rights reserved.
//

import UIKit

class ViewController: UIViewController, UIPickerViewDelegate, UIPickerViewDataSource {
    
    //list of areas for picker
    let areas = ["Eldorado Canyon", "Boulder Canyon", "Shelf Road", "Rocky Mountain National Park", "Golden"]
    let areaAbbrevs = ["Eldorado Canyon", "Boulder Canyon", "Shelf Road", "Rocky Mountain NP", "Golden"]
    
    //currently selected area
    var selectedArea = String()
    var selectedAbbrev = String()
    var lat = String()
    var long = String()
    
    
    //data controller
    var routeDC = RouteDataController()
    
    //local copy of data
    var data = [Route]()

    @IBAction func searchRoutes(_ sender: Any) {
        do {
            //start loading the data
            try routeDC.loadJSON(lat: lat, long: long)
            
            //block user events and display spinner
            let alert = UIAlertController(title: nil, message: "Searching in \(selectedAbbrev)...", preferredStyle: .alert)
            let loadingIndicator = UIActivityIndicatorView(frame: CGRect(x: 0, y: 5, width: 50, height: 50))
            loadingIndicator.hidesWhenStopped = true
            loadingIndicator.style = UIActivityIndicatorView.Style.medium
            loadingIndicator.startAnimating()
            alert.view.addSubview(loadingIndicator)
            present(alert, animated: true, completion: nil)
        } catch {
            print(error)
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        selectedArea = areas[0]
        selectedAbbrev = areaAbbrevs[0]
        lat = "39.93"
        long = "-105.29"
        
        //set function to notify when response is complete
        routeDC.onDataUpdate = {[weak self] (data: [Route]) in
            self?.searchDone(routes: data)
        }
    }

    //called when the json data has been parsed
    func searchDone(routes: [Route]){
        //dismiss loading alert
        dismiss(animated: true, completion: {self.dismissDone(routes: routes)})
    }
    
    func dismissDone(routes: [Route]){
        //set data property
        data = routes
        
        performSegue(withIdentifier: "SearchResults", sender: nil)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        //check id of segue
        if segue.identifier == "SearchResults" {
            //downcast destination as results table view controller
            let dest = segue.destination as! ResultsTableViewController
            //set title
            dest.title = "Routes in \(selectedAbbrev)"
            //pass data
            dest.results = data
        }
    }
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return areas.count
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return areas[row]
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        selectedArea = areas[row]
        selectedAbbrev = areaAbbrevs[row]
        if selectedArea == areas[0] {
            lat = "39.93"
            long = "-105.29"
        } else if selectedArea == areas[1]{
            lat = "40.00"
            long = "-105.41"
        } else if selectedArea == areas[2]{
            lat = "38.63"
            long = "-105.23"
        } else if selectedArea == areas[3]{
            lat = "40.29"
            long = "-105.67"
        } else if selectedArea == areas[4]{
            lat = "39.74"
            long = "-105.30"
        }
    }
}

