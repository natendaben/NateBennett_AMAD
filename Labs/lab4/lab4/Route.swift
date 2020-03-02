//
//  Route.swift
//  lab4
//
//  Created by Nathanael Bennett on 2/28/20.
//  Copyright © 2020 Nathanael Bennett. All rights reserved.
//

import Foundation

struct Route: Decodable {
    let name: String
    let type: String
    let rating: String
    let stars: Double
    let pitches: Int
}

struct RouteData: Decodable {
    var routes = [Route]()
}
