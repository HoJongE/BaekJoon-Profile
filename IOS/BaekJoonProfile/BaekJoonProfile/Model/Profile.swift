//
//  Profile.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/15.
//

import Foundation

struct Profile: Codable, CustomStringConvertible {
    var description: String {
        get {
            "아이디: \(handle) 자기소개: \(bio) 클래스: \(Class) 푼 문제: \(solvedCount) 등수: \(rank)"
        }
    }
    
    var handle: String
    var bio : String
    var badge : Badge?
    var background : Background?
    var profileImageUrl : String?
    var Class : Int
    var classDecoration : String
    var solvedCount : Int
    var voteCount : Int
    var exp : UInt64
    var tier : Int
    var rating:Int
    var maxStreak: Int
    var rank :Int
    
    enum CodingKeys : String, CodingKey{
        case handle
        case bio
        case badge
        case background
        case profileImageUrl
        case Class = "class"
        case classDecoration
        case solvedCount
        case voteCount
        case exp
        case tier
        case rating
        case maxStreak
        case rank
    }
}

struct Badge : Codable{
    var badgeId:String?
    var badgeImageUrl:String?
    var displayName:String?
    var displayDescription:String?
}

struct Background : Codable{
    var backgroundImageUrl:String?
    var author:String?
    var displayName:String?
    var displayDescription:String?
}
