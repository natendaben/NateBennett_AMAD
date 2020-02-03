//
//  TaskCollection.swift
//  lab1
//
//  Created by Nathanael Bennett on 2/3/20.
//  Copyright © 2020 Nathanael Bennett. All rights reserved.
//

import Foundation

struct TaskCollection: Decodable {
    let name: String
    let taskitems: [String]
}
