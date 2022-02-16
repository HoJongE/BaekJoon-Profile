//
//  ContentView.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/10.
//

import SwiftUI
/*:
 # Main View Container
 */
struct ContentView: View {
    @EnvironmentObject var profileViewModel : ProfileViewModel
    
    var body: some View {
        LoginView()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        Group{
            ContentView()
                .previewDevice(PreviewDevice(rawValue: "iPhone 12 Pro"))
                .previewDisplayName("iPhone 12 pro")
            ContentView()
                .previewDevice(PreviewDevice(rawValue: "iPhone 8"))
                .previewDisplayName("iPhone 8")
                .environment(\.colorScheme, .dark)
        }
    }
}
