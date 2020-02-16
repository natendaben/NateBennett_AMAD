//
//  RangeStruct.swift
//  lab2
//
//  Created by Nathanael Bennett on 2/15/20.
//  Copyright © 2020 Nathanael Bennett. All rights reserved.
//

import Foundation

struct MountainRange: Codable {
    var totalNumber: String
    var range: String
    var mountains: [String]
}

enum DataError: Error {
    case ProblemDecoding
    case ProblemWithDataFile
    case ProblemEncoding
}

class MountainRangeController {
    var mountainRanges = [MountainRange]() //create array of mountain ranges
    let filename = "fourteeners"
    let dataFilename = "data.plist"
    
    //function to load data
    func loadData() throws {
        let pathURL: URL?
        let dataFileURL = getDataFile(dataFile: dataFilename) //get path where our data file might be if it exists
        if FileManager.default.fileExists(atPath: dataFileURL.path){ //if a new data file exists
            pathURL = dataFileURL //if exists, set our path URL to our data file URL
        } else { //otherwise, we'll use default plist
            pathURL = Bundle.main.url(forResource: filename, withExtension: "plist")
        }
        //get our filename
        if let dataURL = pathURL{
            //create decoder instance
            let dataDecoder = PropertyListDecoder()
            do {
                let data = try Data(contentsOf: dataURL) //try to get our raw data
                mountainRanges = try dataDecoder.decode([MountainRange].self, from: data) //try to decode our data into an array of mountain ranges
            } catch {
                throw DataError.ProblemDecoding
            }
        } else { //if file path doesn't work, throw error
            throw DataError.ProblemWithDataFile
        }
    }
    
    //function to get list of mountain ranges
    func getMountainRanges() -> [String]{
        var mountainRangeList = [String]() //make empty array for storing ranges
        for range in mountainRanges { //go through list and add each range to list
            mountainRangeList.append(range.range)
        }
        return mountainRangeList
    }
    
    //function to get list of mountains in specific range
    func getMountains(index: Int) -> [String]{
        return mountainRanges[index].mountains
    }
    
    func getTotalMountainsInRange(index: Int) -> String {
        return mountainRanges[index].totalNumber
    }
    
    //function to add mountain
    func addMountain(rangeIndex: Int, mountain: String, mountainIndex: Int){
        mountainRanges[rangeIndex].mountains.insert(mountain, at: mountainIndex)
    }
    
    //function to remove mountain
    func removeMountain(rangeIndex: Int, mountainIndex: Int){
        mountainRanges[rangeIndex].mountains.remove(at: mountainIndex)
    }
    
    //for data persistence
    func getDataFile(dataFile: String) -> URL {
        let directoryPath = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask) //get path for data file
        let docDirectory = directoryPath[0] //documents directory
        
        //return url for our plist
        return docDirectory.appendingPathComponent(dataFile)
    }
    
    func writeData() throws {
        let dataFileURL = getDataFile(dataFile: dataFilename) //use our helper function to get our url
        let encoder = PropertyListEncoder() //encoder instance
        encoder.outputFormat = .xml //set output format
        
        do {
            let data = try encoder.encode(mountainRanges.self)
            try data.write(to: dataFileURL)
        } catch {
            print(error)
            throw DataError.ProblemEncoding
        }
        
        
    }
}
