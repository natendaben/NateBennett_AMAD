//
//  TaskDataLoader.swift
//  lab1
//
//  Created by Nathanael Bennett on 2/3/20.
//  Copyright © 2020 Nathanael Bennett. All rights reserved.
//

import Foundation

enum DataError: Error {
    case BadFilePath
    case ProblemDecodingData
    case NoData
}

class TaskDataLoader {
    //properties
    
    var taskItemsData: [TaskCollection]?
    let filename = "tasks" //name of plist file
    
    //load all of the data from our plist
    func loadTaskData() throws {
        print("Loading data now!")
        //get our file path
        if let filePathURL = Bundle.main.url(forResource: filename, withExtension: "plist") {
            let decoder = PropertyListDecoder() //create instance of decoder
            do {
                //get our file contents
                let taskData = try Data(contentsOf: filePathURL) //get data from URL that we found
                taskItemsData = try decoder.decode([TaskCollection].self, from: taskData) //decode data into our class variable taskItemsData, which is an array of TaskCollection structs
                print("Data loaded successfully")
            } catch {
                throw DataError.ProblemDecodingData
            }
        } else { //if file path could not be accessed
            throw DataError.BadFilePath
        }
    }
    
    //function for returning task categories in array of strings
    func getTaskCategories() throws -> [String] {
        var taskCategories = [String]()
        
        //make sure we got our data correctly
        if let data = taskItemsData {
            for category in data{
                taskCategories.append(category.name) //add name of category to our category list
            }
            return taskCategories //return our list of categories
        } else {
            //data isn't working for some reason, throw an error!
            throw DataError.NoData
        }
    }
    
    //function for returning the tasks for any category, or throw an error
    func getTasksInCategory(index: Int) throws -> [String]{
        //make sure we got our data correctly
        if let data = taskItemsData {
            return data[index].taskitems //return the array of task items
        } else {
            //data isn't working for some reason, throw an error!
            throw DataError.NoData
        }
    }
}
