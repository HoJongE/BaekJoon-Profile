//
//  RootView.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/20.
//

import SwiftUI

struct RootView: View {
    @EnvironmentObject var profileViewModel : ProfileViewModel
    @State var showSheet : Bool = false
    var body: some View {
        ZStack(alignment:.bottomTrailing){
            if case DataState.Success(data: let profile) = profileViewModel.profileState {
                ProfileView(profile: profile, logout: profileViewModel.logout)
            } else {
                LoginView()
                    .environmentObject(profileViewModel)
            }
            
            GuideButton {
                showSheet = true
            }
            .padding()
        }
        .sheet(isPresented: $showSheet) {
            BottomSheetContainer(title: "위젯 추가 안내", isPresent: $showSheet){
                GuideView()
            }
        }
    }
}

struct RootView_Previews: PreviewProvider {
    static var previews: some View {
        RootView()
            .environmentObject(ProfileViewModel())
    }
}
