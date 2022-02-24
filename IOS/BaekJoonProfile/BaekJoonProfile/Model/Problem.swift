//
//  Problem.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/23.
//

import Foundation

struct Problem : Codable {
    let problemId : Int
    let titleKo : String
    let isSolvable : Bool
    let isPartial : Bool
    let acceptedUserCount : Int
    let level : Int
    let votedUserCount : Int
    let isLevelLocked : Bool
    let averageTries : Double
    let tags : [ProblemTag]
}

extension Problem : CustomStringConvertible {
    var description: String {
        "문제 아이디 : \(problemId) 제목 : \(titleKo) 티어 : \(Profile.getTierName(tier: level)) 태그: \(tags.description)"
    }
}



#if DEBUG
extension Problem {
    static func provideDummyProblem() -> Problem {
        Problem(problemId: 1324, titleKo: "기본 bfs", isSolvable: true, isPartial: false, acceptedUserCount: 251, level: 21, votedUserCount: 251, isLevelLocked: true, averageTries: 3.81, tags: [ProblemTag(key: "152", bojTagId: 124, displayNames: [ProblemTag.DisplayName(language: "ko", name: "아호 코라식", short: "bfs")]),ProblemTag(key: "152", bojTagId: 124, displayNames: [ProblemTag.DisplayName(language: "ko", name: "다익스트라", short: "bfs")]),ProblemTag(key: "152", bojTagId: 124, displayNames: [ProblemTag.DisplayName(language: "ko", name: "bfs+dfs", short: "bfs")]),ProblemTag(key: "152", bojTagId: 124, displayNames: [ProblemTag.DisplayName(language: "ko", name: "bfs", short: "bfs")])])
    }
}
#endif
