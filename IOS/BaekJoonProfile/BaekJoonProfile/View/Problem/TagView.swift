//
//  TagView.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/23.
//

import SwiftUI

struct TagView: View {
    let tag : ProblemTag
    
    var body: some View {
        Text(tag.displayNames[0].name)
            .captionText(textColor: .white)
            .padding(.init(top: 12, leading: 8, bottom: 12, trailing: 8))
            .background(RoundedRectangle(cornerRadius: 8).foregroundColor(.gray))
    }
}

struct TagView_Previews: PreviewProvider {
    static var previews: some View {
        TagView(tag: ProblemTag(key: "152", bojTagId: 231, displayNames: [ProblemTag.DisplayName(language: "kr", name: "다익스트라", short: "다익스트라")]))
    }
}
