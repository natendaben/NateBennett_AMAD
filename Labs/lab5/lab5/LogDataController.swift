//
//  LogDataController.swift
//  lab5
//
//  Created by Nathanael Bennett on 3/18/20.
//  Copyright © 2020 Nathanael Bennett. All rights reserved.
//

import Foundation
import Firebase

struct SleepLog {
    var date: Date
    var length: Double
    var relaxation: String
    var id: String
    
    func getDate() -> String {
        let formatter = DateFormatter()
        formatter.dateStyle = .short
        return formatter.string(from: date)
    }
}

class LogDataController {
    //reference to database
    var db: Firestore!
    
    //store data locally
    var sleepData = [SleepLog]()
    
    //closure to notify data controller of data changes
    var onDataUpdate: (([SleepLog]) -> Void)!
    
    init() {
        //use default settings
        let settings = FirestoreSettings()
        Firestore.firestore().settings = settings
        
        //get reference to database
        db = Firestore.firestore()
    }
    
    //fetch data and listen for any updates
    func loadData() {
        db.collection("sleeps").addSnapshotListener { querySnapshot, error in
            //make sure we got the collection
            guard let collection = querySnapshot else {
                print("Error fetching collection: \(error!)")
                return
            }
            
            //get the documents
            let documents = collection.documents
            
            //empty data out
            self.sleepData.removeAll()
            
            //append updated data to our list
            for document in documents {
                //get data from the document
                let data = document.data()
                
                //get the data fields and downcast to appropriate types
                let date = (data["date"] as! Timestamp).dateValue()
                let length = data["length"] as? Double
                let relaxation = data["relaxation"] as! String
                
                //get id
                let id = document.documentID
                
                //construct object
                let sleepLog = SleepLog(date: date, length: length ?? 0.0, relaxation: relaxation, id: id)
                
                self.sleepData.append(sleepLog)
            }
            self.onDataUpdate(self.sleepData)
        }
    }
    
    func writeData(date: Date, length: Double, relaxation: String){
        //add new document with generated id
        db.collection("sleeps").addDocument(data: [
            "date": Timestamp(date: date),
            "length": length,
            "relaxation": relaxation
            ], completion: { err in
                if let err = err {
                    print("Error adding document: \(err)")
                } else {
                    print("Document added successfully!")
                }
        }
        )
    }
    
    func deleteLog(logID: String){
        //delete log from firebase
        db.collection("sleeps").document(logID).delete() { err in
            if let err = err {
                print("Error removing document: \(err)")
            } else {
                print("Document successfully removed!")
            }
        }
    }
    
}

