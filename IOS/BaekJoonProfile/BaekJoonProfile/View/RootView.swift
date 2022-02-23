//
//  RootView.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/20.
//

import SwiftUI

struct RootView: View {
    @EnvironmentObject var profileViewModel : ProfileViewModel
    
    var body: some View {
        VStack{
            if case DataState.Success(data: let profile) = profileViewModel.profileState {
                ProfileHost(profile: profile, logout: profileViewModel.logout)
                    .transition(.profileTransition)
            } else {
                LoginView()
            }
        }
        .background(Color.backgroundColor.edgesIgnoringSafeArea(.all))
        .ignoresSafeArea(.keyboard)
    }
}
extension AnyTransition {
    static var profileTransition : AnyTransition {
        .move(edge: .bottom)
        .combined(with: .fade)
    }
}

struct RootView_Previews: PreviewProvider {
    static var previews: some View {
        let profileViewModel : ProfileViewModel = ProfileViewModel()
        let sheetManger : SheetManager = SheetManager()
        Group {
            ForEach(PreviewDevice.previewDevices,id: \.self){
                RootView()
                    .environmentObject(profileViewModel)
                    .environmentObject(sheetManger)
                    .previewDevice(PreviewDevice(rawValue: $0))
                    .previewDisplayName($0)
            }
        }
    }
}
