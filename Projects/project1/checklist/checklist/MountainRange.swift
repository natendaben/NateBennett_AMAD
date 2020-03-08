//
//  MountainRange.swift
//  checklist
//
//  Created by Nathanael Bennett on 3/5/20.
//  Copyright © 2020 Nathanael Bennett. All rights reserved.
//

import Foundation
import UIKit

struct MountainRange: Codable {
    var totalNumber: String
    var range: String
    var mountains: [Mountain]
}

struct Mountain: Codable {
    var name: String
    var elevation: String
    var rank: Int
    var imageName: String
    var notes: String
    var peakClass: Int
    var ticks: Int
    
}

enum DataError: Error {
    case ProblemDecoding
    case ProblemWithDataFile
    case ProblemEncoding
}

class MountainDataController {
    var mountainRanges = [MountainRange]() //create array of mountain ranges
    let filename = "fourteeners"
    let dataFilename = "data.plist"
    
    init() {
        //get our app instance
        let app = UIApplication.shared
        //subscribe to willResignActive notification
        NotificationCenter.default.addObserver(self, selector: #selector(MountainDataController.writeData(_:)), name: UIApplication.willResignActiveNotification, object: app)
    }
    
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
    
    func getMountainRange(rangeIndex: Int) -> MountainRange {
        return mountainRanges[rangeIndex]
    }
    
    //function to get list of mountain ranges
    func getMountainRangeList() -> [String]{
        var mountainRangeList = [String]() //make empty array for storing ranges
        for range in mountainRanges { //go through list and add each range to list
            mountainRangeList.append(range.range)
        }
        return mountainRangeList
    }
    
    //function to get list of mountains in specific range
    func getMountainListForRange(index: Int) -> [String]{
        let mountainList = mountainRanges[index].mountains
        var newList = [String]()
        for mountain in mountainList {
            newList.append(mountain.name)
        }
        return newList
    }
    
    func getMountainsInRange(index: Int) -> [Mountain] {
        return mountainRanges[index].mountains
    }
    
    func getMountain(rangeIndex: Int, mountainIndex: Int) -> Mountain {
        let mountain = mountainRanges[rangeIndex].mountains[mountainIndex]
        return mountain
    }
    
    //for data persistence
    func getDataFile(dataFile: String) -> URL {
        let directoryPath = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask) //get path for data file
        let docDirectory = directoryPath[0] //documents directory
        
        //return url for our plist
        return docDirectory.appendingPathComponent(dataFile)
    }
    
    @objc func writeData(_ notification: NSNotification) throws {
        print("Writing data to \(dataFilename)")
        let dataFileURL = getDataFile(dataFile: dataFilename)
        //get an encoder
        let encoder = PropertyListEncoder()
        //set format — plist is a type of xml
        encoder.outputFormat = .xml
        do {
            let data = try encoder.encode(mountainRanges.self)
            try data.write(to: dataFileURL)
        } catch {
            print(error)
            throw DataError.ProblemEncoding
        }
        
    }
    
    func updateMountainInfo(notes: String, ticks: Int, mountainIndex: Int, rangeIndex: Int){
        var mountain = mountainRanges[rangeIndex].mountains[mountainIndex]
        //print("Old notes: \(mountain.notes)")
        mountain.notes = notes
        mountain.ticks = ticks
        mountainRanges[rangeIndex].mountains[mountainIndex] = mountain
        //print("New notes: \(mountain.notes)")
        //print("New notes again: \(mountainRanges[rangeIndex].mountains[mountainIndex].notes)")
    }
}
