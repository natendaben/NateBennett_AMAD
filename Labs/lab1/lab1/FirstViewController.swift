//
//  FirstViewController.swift
//  lab1
//
//  Created by Nathanael Bennett on 2/3/20.
//  Copyright © 2020 Nathanael Bennett. All rights reserved.
//

import UIKit

class FirstViewController: UIViewController, UIPickerViewDelegate, UIPickerViewDataSource{

    @IBOutlet weak var taskPicker: UIPickerView!
    @IBOutlet weak var taskLabel: UILabel!
    
    //define an integer for each component
    let taskCategoryComponent = 0
    let taskItemComponent = 1
    
    //create instance of our data controller and some arrays for our data
    var dataLoader = TaskDataLoader()
    var categories = [String]()
    var taskitems = [String]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        //load the data and save it to our variables
        do {
            try dataLoader.loadTaskData()
            categories = try dataLoader.getTaskCategories()
            taskitems = try dataLoader.getTasksInCategory(index: 0) //for the first time, we'll load the taskitems in the first category which is index 0
        } catch {
            print("Error loading data")
        }
        
        taskLabel.text = "Looks like you're going to focus on relaxation today. Your first task is to read a book. Good luck!"
        
        
    }

    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 2 //using 2 components for this picker
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        if component == taskCategoryComponent {
            return categories.count
        } else if component == taskItemComponent {
            return taskitems.count
        } else {
            print("Unknown component")
            return -1
        }
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        if component == taskCategoryComponent {
            return categories[row]
        } else if component == taskItemComponent {
            return taskitems[row]
        } else {
            print("Unknown Component")
            return "Unknown Component"
        }
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        //we need to update our task items if the user selects a different category
        if component == taskCategoryComponent {
            do {
                taskitems = try dataLoader.getTasksInCategory(index: row)
            } catch {
                print("Error getting tasks for that category")
            }
            
            //update our component and select the first row in new category
            taskPicker.reloadComponent(taskItemComponent) //reload task items
            taskPicker.selectRow(0, inComponent: taskItemComponent, animated: true) //select the first item in the new category
        }
        
        //user output
        let category = pickerView.selectedRow(inComponent: taskCategoryComponent)
        let task = pickerView.selectedRow(inComponent: taskItemComponent)
        
        taskLabel.text = "Looks like you're going to focus on \(categories[category]) today. Your first task is to \(taskitems[task]). Good luck!"
    }
    
}

