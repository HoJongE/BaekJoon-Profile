//
//  ProblemDescription.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/23.
//

import SwiftUI

struct ProblemDescription: View {
    let problem : Problem
    @State private var showTag : Bool = false
    
    var body: some View {
        
        ScrollView(.vertical, showsIndicators: false) {
            Text(problem.titleKo)
                .font(.custom("GmarketSansTTFBold", size: 20))
                .offset(x: 0, y: -3)
                .padding(.vertical,3)
            
            SectionView(sectionTitle: "문제 정보"){}
            
            ProblemDescriptionRow(title: "문제 번호", content: "\(problem.problemId) 번")
            
            Spacer().frame(height:10)
            
            ProblemDescriptionRow(title: "맞힌 사람", content: "\(problem.acceptedUserCount) 명")
            
            Spacer().frame(height:10)
            
            ProblemDescriptionRow(title: "평균 시도", content: String(problem.averageTries))
            
            SectionView(sectionTitle: "태그"){
                
                Button(action:{
                    withAnimation(.spring()) {
                        showTag.toggle()
                    }
                }){
                    HStack{
                        Spacer()
                        Image(systemName: "arrow.backward.circle")
                            .rotationEffect(.degrees(showTag ? -90 : 0))
                            .imageScale(.large)
                    }
                }
                
            }
            if showTag {
                TagRow(problem: problem)
                    .transition(.opacity.combined(with: .offset(x: 0, y: -70)))
                    .padding()
                
            }
        }
        .background(Color.backgroundColor.edgesIgnoringSafeArea(.all))
        .frame(maxWidth:.infinity)
        .foregroundColor(.white)
    }
}

extension AnyTransition {
    
}

struct SectionView <Content:View> : View {
    let sectionTitle : String
    let content : () -> Content
    
    init(sectionTitle : String,@ViewBuilder content:@escaping () -> Content) {
        self.sectionTitle = sectionTitle
        self.content = content
    }
    
    var body: some View {
        HStack{
            Text(sectionTitle)
                .captionText(textColor: .white)
                .offset(y:2)
            Spacer()
            content()
        }
        .frame(height:20)
        .padding(.init(top: 8, leading: 16, bottom: 8, trailing: 16))
        .background(Color.black)
        .cornerRadius(16)
        .padding(8)
        
    }
}

struct ProblemDescriptionRow : View {
    let title : String
    let content : String
    
    var body: some View {
        
        HStack(spacing : 10) {
            Text(title)
                .captionText(textColor: .white)
                .frame(width:80,alignment: .leading)
            Text(content)
                .captionText(textColor: .white)
            Spacer()
        }
        .padding(.horizontal,24)
        
    }
}

#if DEBUG
struct ProblemDescription_Previews: PreviewProvider {
    static var previews: some View {
        ProblemDescription(problem: Problem.provideDummyProblem())
    }
}
#endif
