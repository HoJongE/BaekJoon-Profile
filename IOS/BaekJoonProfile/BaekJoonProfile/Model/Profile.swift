//
//  Profile.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/15.
//

import Foundation
import SwiftUI

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

extension Profile {
    static func provideDummyData() -> Profile {
        Profile(handle: "as00098", bio: "안녕하세요\n하하하", badge: nil, background: Background(backgroundImageUrl:"https://static.solved.ac/profile_bg/_season2020/s2020-gold4.png"), profileImageUrl: nil, Class: 4, classDecoration: "gold", solvedCount: 571, voteCount: 21, exp: 102391209, tier: 26, rating: 1908, maxStreak: 72, rank: 1700)
    }
    
    func getTierName() -> String {
        switch self.tier {
        case 1...5: return "Bronze"
        case 6...10: return "Silver"
        case 11...15: return "Gold"
        case 16...20: return "Platinum"
        case 21...25: return "Diamond"
        case 26...30: return "Ruby"
        default: return "Master"
        }
    }
    
    func getTierColor() -> Color {
        switch self.tier {
        case 1...5: return .bronze
        case 6...10: return .silver
        case 11...15: return .gold
        case 16...20: return .platinum
        case 21...25 : return .diamond
        case 26...30: return .ruby
        default: return .ruby
        }
    }
    
    func getTierNumber() -> Int {
        tier % 5 == 0 ? 1 : 6 - (tier % 5)
    }
}
