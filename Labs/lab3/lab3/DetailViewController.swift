//
//  DetailViewController.swift
//  lab3
//
//  Created by Nathanael Bennett on 2/28/20.
//  Copyright © 2020 Nathanael Bennett. All rights reserved.
//

import UIKit

class DetailViewController: UIViewController {

    @IBOutlet weak var parkImage: UIImageView!
    
    var imageName: String?
    
    @IBAction func share(_ sender: Any) {
        var imageArray = [UIImage]()
        imageArray.append(parkImage.image!)
        
        let sharePopup = UIActivityViewController(activityItems: imageArray, applicationActivities: nil)
        sharePopup.modalPresentationStyle = .popover
        present(sharePopup, animated: true, completion: nil)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        if let imgName = imageName {
            parkImage.image = UIImage(named: imgName)
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
