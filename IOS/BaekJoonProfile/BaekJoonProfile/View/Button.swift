//
//  Button.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/14.
//

import SwiftUI

struct TextButton: View {
    let text : String
    let onClick:()->Void
    
    var body: some View {
        Button(action: onClick){
            Text(text)
                .modifier(ButtonText())
        }
    }
}

struct Button_Previews: PreviewProvider {
    static var previews: some View {
        TextButton(text:"프로필 조회"){
            
        }
    }
}
