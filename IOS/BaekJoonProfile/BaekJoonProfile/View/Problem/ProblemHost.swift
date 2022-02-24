//
//  ProblemHost.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/23.
//

import SwiftUI

struct ProblemHost: View {
    @ObservedObject private(set) var problemViewModel : ProblemViewModel = ProblemViewModel()
    
    var body: some View {
       
        let success : Bool = {
            if case DataState.Success(data: _) = problemViewModel.problem {
                return true
            } else {
                return false
            }
        }()
        
        VStack {
            Spacer()
            TierBadge(width: 150, tier: problemViewModel.tier)
                .animation(nil)
                .scaleEffect(success ? 0.4 : 1.0)
                .animation(.spring())
            
            if case let DataState.Success(data) = problemViewModel.problem {
                ProblemDescription(problem: data)
                    .transition(.delayedOpacity)
            }
            
            Spacer()
            
            BottomButton(label: "다시 추천 받기",loading:
                            problemViewModel.problem == DataState.Loading){
                problemViewModel.getRecommendedProblem()
            }
            .disabled(problemViewModel.problem == DataState.Loading)
            
        }
        .frame(maxWidth:.infinity,maxHeight: .infinity)
        .background(Color.backgroundColor.edgesIgnoringSafeArea(.all))
    }
}

extension AnyTransition {
    static var delayedOpacity : AnyTransition {
        .asymmetric(insertion: .opacity.animation(.easeInOut.delay(0.5)), removal: .opacity.animation(.easeInOut(duration: 0.2)))
    }
}

struct ProblemHost_Previews: PreviewProvider {
    static var previews: some View {
        let problemViewModel = ProblemViewModel(problemRepository: DefaultProblemRepository.shared, initialState: DataState.Success(data: Problem.provideDummyProblem()))
        
        Group {
            ForEach(PreviewDevice.previewDevices,id: \.self) {
                ProblemHost(problemViewModel: problemViewModel)
                    .previewDevice(PreviewDevice(rawValue: $0))
                    .previewDisplayName($0)
            }
        
        }
    }
}
