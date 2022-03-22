//
//  TagRow.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/24.
//

import SwiftUI

struct TagRow: View {
    let problem : Problem
    
    var body: some View {
        ScrollView (.horizontal,showsIndicators: false){
            HStack {
                ForEach(problem.tags) {
                    TagView(tag: $0)
                }
            }
        }
    }
}
#if DEBUG
struct TagRow_Previews: PreviewProvider {
    static var previews: some View {
        TagRow(problem: Problem.provideDummyProblem())
    }
}
#endif
