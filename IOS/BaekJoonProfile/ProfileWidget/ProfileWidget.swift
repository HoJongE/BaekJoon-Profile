//
//  ProfileWidget.swift
//  ProfileWidget
//
//  Created by 박종호 on 2022/02/18.
//

import WidgetKit
import SwiftUI
import Alamofire


/*
 시간에 따른 업데이트 로직을 담는 구조체
 */
struct Provider: IntentTimelineProvider {
    
    typealias Intent = ProfileIDIntent
    typealias Entry = ProfileEntry
    
    func placeholder(in context: Context) -> ProfileEntry {
        ProfileEntry(date: Date(),profile: DataState.Success(data: Profile.provideDummyData()))
    }
    
    /*
     위젯 갤러리에서 보여질 부분
     */
    func getSnapshot(for configuration: ProfileIDIntent ,in context: Context, completion: @escaping (ProfileEntry) -> ()) {
        let entry = ProfileEntry(date: Date(),profile: DataState.Success(data: Profile.provideDummyData()))
        completion(entry)
    }
    
    /*
     정의한 타임라인에 맞게 업데이트해서 보여질 내용
     */
    func getTimeline(for configuration:ProfileIDIntent ,in context: Context, completion: @escaping (Timeline<Entry>) -> ()) {
        let id = configuration.id ?? ""
        let updateInterval = configuration.updateInterval ?? 60
        let date = Date()
      
        // Generate a timeline consisting of five entries an hour apart, starting from the current date.
        let nextUpdateDate = Calendar.current.date(byAdding: .minute,value: updateInterval.intValue , to: date)!
        
        
        SolvedACService.shared.getProfile(id: id){ result in
            let entry = Entry(date: date, profile: result)
            let timeLine = Timeline(entries: [entry], policy: .after(nextUpdateDate))
            completion(timeLine)
        }
    }
}

struct ProfileEntry: TimelineEntry {
    let date: Date
    let profile : DataState<Profile>
}


/*
 위젯의 타이틀과 부타이틀의 내용이 들어감
 */
@main
struct ProfileWidget: Widget {
    let kind: String = "ProfileWidget"
    
    var body: some WidgetConfiguration {
        
        IntentConfiguration(kind: kind,intent: ProfileIDIntent.self, provider: Provider()) { entry in
            ProfileWidgetEntryView(profileState:entry.profile)
        }
        .configurationDisplayName("백준 프로필")
        .description("자동으로 업데이트되는 프로필 위젯입니다.\n위젯 편집에서 아이디와 업데이트 간격을 설정해주세요!")
        .supportedFamilies([.systemMedium])
    }
}

