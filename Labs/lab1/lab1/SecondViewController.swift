//
//  SecondViewController.swift
//  lab1
//
//  Created by Nathanael Bennett on 2/3/20.
//  Copyright © 2020 Nathanael Bennett. All rights reserved.
//

import UIKit

class SecondViewController: UIViewController {

    //declare schemes for apps or websites we might open
    let appleNewsAppScheme = "applenews://"
    let newYorkTimesScheme = "https://www.nytimes.com/"
    
    //function for opening an app
    func openApp(scheme: String){
        if let url = URL(string: scheme){
            UIApplication.shared.open(url, options: [:], completionHandler: {
                (success) in
                print("Open \(scheme): \(success)")
            })
        }
    }
    
    //function for checking if we have access to a url scheme
    func checkIfSchemeIsAvailable(scheme: String) -> Bool {
        if let url = URL(string: scheme){
            return UIApplication.shared.canOpenURL(url)
        }
        return false
    }
    
    //launch Apple News app if installed, or New York Times webpage if not
    @IBAction func activateMusic(_ sender: Any) {
        let appleNewsAppInstalled = checkIfSchemeIsAvailable(scheme: appleNewsAppScheme) //check to see if apple news app is installed
        
        if appleNewsAppInstalled {
            openApp(scheme: appleNewsAppScheme)
        } else {
            openApp(scheme: newYorkTimesScheme)
        }
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }


}

