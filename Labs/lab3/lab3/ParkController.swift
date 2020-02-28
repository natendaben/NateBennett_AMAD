//
//  ParkController.swift
//  lab3
//
//  Created by Nathanael Bennett on 2/27/20.
//  Copyright © 2020 Nathanael Bennett. All rights reserved.
//

import Foundation

struct Park: Decodable{
    var imageName: String
    var name: String
}

enum DataError: Error {
    case ProblemDecoding
    case ProblemWithDataFile
}

class ParkController {
    var parks = [Park]()
    let filename = "parks"
    
    //function to load data
    func loadData() throws {
        if let filePathURL = Bundle.main.url(forResource: filename, withExtension: "plist") {
            print("Got file path")
            let decoder = PropertyListDecoder()
            do {
                print("Got into do section")
                let data = try Data(contentsOf: filePathURL)
                print("Got data")
                parks = try decoder.decode([Park].self, from: data)
                print("Decoded")
            } catch {
                throw DataError.ProblemDecoding
            }
        } else {
            throw DataError.ProblemWithDataFile
        }
    }
    
    //function to get image names
    func getImages() -> [String] {
        var parkImageList = [String]()
        for park in parks {
            parkImageList.append(park.imageName)
        }
        return parkImageList
    }
    
    func getParkNames() -> [String] {
        var parkNameList = [String]()
        for park in parks {
            parkNameList.append(park.name)
        }
        return parkNameList
    }
}
