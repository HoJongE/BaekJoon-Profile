//
//  Profile.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/15.
//

import Foundation
import SwiftUI

struct Profile: Codable {
  
  
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
extension Profile {
  struct Badge : Codable{
    var badgeId:String?
    var badgeImageUrl:String?
    var displayName:String?
    var displayDescription:String?
  }
}

extension Profile {
  struct Background : Codable{
    var backgroundImageUrl:String?
    var author:String?
    var displayName:String?
    var displayDescription:String?
  }
}
extension Profile : CustomStringConvertible{
  
  
  static func getTierName(tier: Int) -> String {
    switch tier {
      case 0 : return "Unrated"
      case 1...5: return "Bronze \(getTierNumber(tier: tier))"
      case 6...10: return "Silver \(getTierNumber(tier: tier))"
      case 11...15: return "Gold \(getTierNumber(tier: tier))"
      case 16...20: return "Platinum \(getTierNumber(tier: tier))"
      case 21...25: return "Diamond \(getTierNumber(tier: tier))"
      case 26...30: return "Ruby \(getTierNumber(tier: tier))"
      default: return "Master"
    }
  }
  
  static func getTierColor(tier : Int) -> Color {
    switch tier {
      case 0: return .black
      case 1...5: return .bronze
      case 6...10: return .silver
      case 11...15: return .gold
      case 16...20: return .platinum
      case 21...25 : return .diamond
      case 26...30: return .ruby
      default: return .ruby
    }
  }
  
  static func getTierNumber(tier : Int) -> String {
    guard tier != 0 else {
      return "?"
    }
    guard tier != 31 else {
      return "M"
    }
    return String(tier % 5 == 0 ? 1 : 6 - (tier % 5))
  }
  
  var description: String {
    get {
      "아이디: \(handle) 자기소개: \(bio) 클래스: \(Class) 푼 문제: \(solvedCount) 등수: \(rank)"
    }
  }
  
  var backgroundImage : String {
    self.background?.backgroundImageUrl ?? ""
  }
  
  var profileImage : String {
    self.profileImageUrl ?? Const.URL.DEFAULT_PROFILE
  }
  var classUrl: String {
    if self.classDecoration == "none" {
      return "\(Const.URL.CLASS_IMAGE_PREFIX)\(self.Class)\(Const.URL.CLASS_IMAGE_POSTFIX)"
    } else {
      return "\(Const.URL.CLASS_IMAGE_PREFIX)\(self.Class)\(self.classDecoration[self.classDecoration.startIndex])\(Const.URL.CLASS_IMAGE_POSTFIX)"
    }
  }
  
  var badgeImage : String {
    self.badge?.badgeImageUrl ?? ""
  }
  
  var selfDescription : String {
    self.bio.isEmpty ? "자기소개가 없습니다" : self.bio
  }
}

extension Profile {
  static func provideDummyData() -> Profile {
    Profile(handle: "as00098", bio: "안녕하세요\n하하하\n12312312\n12312321\n123123123", badge: nil, background: Background(backgroundImageUrl:"https://static.solved.ac/profile_bg/_season2020/s2020-gold4.png"), profileImageUrl: nil, Class: 4, classDecoration: "gold", solvedCount: 571, voteCount: 21, exp: 102391209, tier: 26, rating: 1908, maxStreak: 72, rank: 1700)
  }
}
