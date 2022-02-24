//
//  ProblemViewModel.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/23.
//

import Foundation

class ProblemViewModel : ObservableObject {
    let problemRepository : ProblemRepository
    
    @Published var problem : DataState<Problem> = DataState.Empty
    @Published var randomTierIndicator : Int = 0
    
    var tier : Int  {
        switch problem {
            case .Success(data: let data): return data.level
            default: return randomTierIndicator
        }
    }
    
    init(problemRepository : ProblemRepository = DefaultProblemRepository.shared,
         initialState : DataState<Problem> = DataState.Empty) {
        self.problemRepository = problemRepository
        self.problem = initialState
        getRecommendedProblem()
    }
    
    public func getRecommendedProblem() {
        problem = DataState.Loading
        
        let timer = Timer.scheduledTimer(withTimeInterval: 0.13, repeats: true){ _ in
            self.randomTierIndicator = Int.random(in: 0...30)
        }
        
        DispatchQueue(label: "problem recommend")
            .asyncAfter(deadline: DispatchTime.now() + 3){
                DispatchQueue.main.async {
                    self.problemRepository.getRandomProblem { result in
                        timer.invalidate()
                        self.problem = result
                        #if DEBUG
                        print(result.description)
                        #endif
                    }
                }
            }
    }
}
