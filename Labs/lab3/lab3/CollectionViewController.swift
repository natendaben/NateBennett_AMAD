//
//  CollectionViewController.swift
//  lab3
//
//  Created by Nathanael Bennett on 2/27/20.
//  Copyright © 2020 Nathanael Bennett. All rights reserved.
//

import UIKit

private let reuseIdentifier = "Cell"

class CollectionViewController: UICollectionViewController, UICollectionViewDelegateFlowLayout {

    //variables for images
    var parkImages = [String]()
    var parkNames = [String]()
    let controller = ParkController()
    
    //variables for layout
    let spacing: CGFloat = 8
    let numberOfItemsPerRow: CGFloat = 3
    let spacingBetweenCells: CGFloat = 8
    
    override func viewDidLoad() {
        super.viewDidLoad()

        //get image names
        do {
            try controller.loadData()
            parkImages = controller.getImages()
            parkNames = controller.getParkNames()
        } catch {
            print(error)
        }

        //layout setup
        let layout = UICollectionViewFlowLayout()
        layout.sectionInset = UIEdgeInsets(top: spacing, left: spacing, bottom: spacing, right: spacing)
        layout.minimumLineSpacing = spacing
        layout.minimumInteritemSpacing = spacing
        collectionView.collectionViewLayout = layout
        
        // Register cell classes - don't need this line because we registered cells with id in main.storyboard
        //self.collectionView!.register(UICollectionViewCell.self, forCellWithReuseIdentifier: reuseIdentifier)
    }

    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "showDetail" {
            let detailViewController = segue.destination as! DetailViewController
            let indexPath = collectionView.indexPath(for: sender as! CollectionViewCell)
            detailViewController.imageName = parkImages[indexPath!.row]
            print(detailViewController.imageName!)
            detailViewController.title = parkNames[indexPath!.row]
        }
    }

    // MARK: UICollectionViewDataSource

    override func numberOfSections(in collectionView: UICollectionView) -> Int {
        return 1
    }


    override func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return parkImages.count
    }

    override func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: reuseIdentifier, for: indexPath) as! CollectionViewCell
        cell.imageView.image = UIImage(named: parkImages[indexPath.row])
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        let totalSpacing = (2*spacing) + ((numberOfItemsPerRow-1)*spacingBetweenCells)
        let width = (collectionView.bounds.width - totalSpacing)/numberOfItemsPerRow
        return CGSize(width: width, height: width)
    }
    
    //HEADER
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, referenceSizeForHeaderInSection section: Int) -> CGSize {
        return CGSize.init(width: 50, height: 50)
    }
    
    //FOOTER
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, referenceSizeForFooterInSection section: Int) -> CGSize {
        return CGSize.init(width: 50, height: 50)
    }
    
    override func collectionView(_ collectionView: UICollectionView, viewForSupplementaryElementOfKind kind: String, at indexPath: IndexPath) -> UICollectionReusableView {
        var header: HeaderSupplementaryView?
        var footer: FooterSupplementaryView?
        if kind == UICollectionView.elementKindSectionHeader{
            header = collectionView.dequeueReusableSupplementaryView(ofKind: kind, withReuseIdentifier: "Header", for: indexPath) as? HeaderSupplementaryView
            header?.headerLabel.text = "National Parks"
            return header!
        } else {
            footer = collectionView.dequeueReusableSupplementaryView(ofKind: kind, withReuseIdentifier: "Footer", for: indexPath) as? FooterSupplementaryView
            footer?.footerLabel.text = "National Parks Count: \(parkImages.count)"
            return footer!
        }
    }
}
