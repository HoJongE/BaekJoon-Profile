//
//  ProblemRepository.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/23.
//

import Foundation

class DefaultProblemRepository : ProblemRepository {
    let solvedACService : SolvedACService
    
    init(solvedACService : SolvedACService = SolvedACService.shared){
        self.solvedACService = solvedACService
    }
    
    public static let shared = DefaultProblemRepository(solvedACService: SolvedACService.shared)
    
    func getRandomProblem(completion: @escaping (DataState<Problem>) -> Void) {
        solvedACService.getRandomProblem(completion: completion)
    }
    
}

protocol ProblemRepository {
    func getRandomProblem(completion: @escaping (DataState<Problem>) -> Void)
}
