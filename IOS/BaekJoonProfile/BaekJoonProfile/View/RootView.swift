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
    @State var bottomSheetAction : BottomSheetAction = .IdList
    var body: some View {
        VStack{
            if case DataState.Success(data: let profile) = profileViewModel.profileState {
                ProfileView(profile: profile, logout: profileViewModel.logout)
            } else {
                LoginView()
                    .environmentObject(profileViewModel)
            }
            HStack{
                RecentIdButton {
                    self.bottomSheetAction = .IdList
                    showSheet = true
                }
                Spacer()
                GuideButton {
                    self.bottomSheetAction = .Description
                    showSheet = true
                }
            }
            .padding(16)
        }
        .background(Color.backgroundColor.edgesIgnoringSafeArea(.all))
        .sheet(isPresented: $showSheet) {
            BottomSheetContainer(title: bottomSheetAction.rawValue, isPresent: $showSheet){
                switch bottomSheetAction {
                    case .Description:
                        GuideView()
                    case .IdList:
                        IdListView(idList: profileViewModel.profileIdList,
                                   onDelete: onDelete(at:),
                                   onMove: onMove(fromSource:to:)
                        ) {id in
                            showSheet = false
                            profileViewModel.getProfile(id: id)
                        }
                        .background(Color.backgroundColor)
                }
            }
        }
        .ignoresSafeArea(.keyboard)
    }
    func onDelete(at indexSet: Optional<IndexSet>) {
        profileViewModel.deleteId(indexSet: indexSet)
    }
    func onMove(fromSource : IndexSet, to destination : Int) {
        profileViewModel.moveId(from: fromSource, to: destination)
        
    }
    enum BottomSheetAction:String {
        case Description = "위젯 추가 안내"
        case IdList = "최근 조회목록"
    }
}

struct RootView_Previews: PreviewProvider {
    static var previews: some View {
        RootView()
            .environmentObject(ProfileViewModel())
        RootView()
            .environmentObject(ProfileViewModel(dataState: DataState.Success(data: Profile.provideDummyData())))
            .previewDevice(PreviewDevice(rawValue: "iPhone 8"))
    }
}
