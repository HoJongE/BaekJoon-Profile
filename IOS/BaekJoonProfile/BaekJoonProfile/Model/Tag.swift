//
//  Tag.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/23.
//

import Foundation

struct ProblemTag : Codable, Identifiable {
  let id: UUID = UUID()
  let key : String
  let bojTagId : Int
  let displayNames : [DisplayName]
}

extension ProblemTag {
  struct DisplayName :Codable {
    let language : String
    let name : String
    let short : String
  }
}

extension ProblemTag : CustomStringConvertible {
  var description: String {
    "태그 목록: \(displayNames.description)"
  }
}

extension ProblemTag.DisplayName : CustomStringConvertible{
  var description: String {
    "태그 이름 : \(name)"
  }
}
