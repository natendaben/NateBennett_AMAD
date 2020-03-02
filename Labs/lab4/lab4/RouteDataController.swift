//
//  RouteDataController.swift
//  lab4
//
//  Created by Nathanael Bennett on 2/28/20.
//  Copyright © 2020 Nathanael Bennett. All rights reserved.
//

import Foundation

enum JSONError: Error {
    case BadURL
    case BadResponse
    case CouldNotParse
}
class RouteDataController {
    //stores all routes from response
    var currentRoutes = RouteData()
    //closure to notify view controller when JSON has been parsed
    var onDataUpdate: ((_ data: [Route]) -> Void)?
    
    //make JSON request based one which area is selected
    func loadJSON(lat: String, long: String) throws {
        //construct URL
        let urlPath = "https://www.mountainproject.com/data/get-routes-for-lat-lon?lat=\(lat)&lon=\(long)&maxDistance=2&minDiff=5.6&maxDiff=5.11c&maxResults=17&key=111604789-31b97449a8120ab87ebd2362e456780c"
        //use guard statement to make sure url is valid
        guard let url = URL(string: urlPath) else {
            throw JSONError.BadURL
        }
        let group = DispatchGroup()
        group.enter()
        
        //make the request and give it a closure for the completion handler
        let session = URLSession.shared.dataTask(with: url, completionHandler: {(data, response, error) in
            //downcast to URLResponse since we made an https response
            let httpResponse = response as! HTTPURLResponse
            
            //get status code
            let statusCode = httpResponse.statusCode
            
            //check status
            guard statusCode == 200 else {
                print("File download error")
                return
            }
            
            //otherwise, download is complete!
            print("Download complete")
            
            //call our parseJSON function asynchronously
            DispatchQueue.main.async {
                self.parseJSON(rawData: data!)
            }
        })
        
        //must call resume to run session and execute request
        session.resume()
    }
    
    //function to parse the JSON and notify view controller
    func parseJSON(rawData: Data){
        do {
            //try to decode response
            let parsedData = try JSONDecoder().decode(RouteData.self, from: rawData)
            print("got here")
            //clear out old data
            currentRoutes.routes.removeAll()
            //add all of the route entries to class property that stores current routes
            for route in parsedData.routes {
                currentRoutes.routes.append(route)
            }
//            for route in currentRoutes.routes {
//                print("Name" + route.name)
//                print("Pitches" + String(route.pitches))
//                print("Rating" + route.rating)
//                print("Type" + route.type)
//                print("Stars" + String(route.stars))
//            }
        } catch {
            print("Error parsing JSON")
            print(error.localizedDescription)
        }
        
        print("Parsing JSON done!")
        
        //pass data back to requesting object
        onDataUpdate?(currentRoutes.routes)
    }
}
