//
//  NewsItemDetailScreen.swift
//  iosApp
//
//  Created by Artem on 2.09.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NewsItemDetailScreen: View {
    
    
    @ObservedObject private var viewImpl: ViewImpl
    
    init(newsItemId: String) {
        let presenter = koin.get(objCClass: NewsItemContractPresenter.self, parameter: newsItemId) as! NewsItemContractPresenter
        viewImpl = ViewImpl(_presenter: presenter)
    }
    
    var body: some View {
        NavigationView {
            VStack {
                switch (viewImpl.state) {
                case .loading:
                    ProgressView()
                case .error(let message):
                    Text(message)
                case .loaded(let item):
                    NewsItemDetailsView(item: item, onOpenClicked: viewImpl.onOpenClicked)
                }
            }.navigationTitle("News item details")
        }.onAppear(perform: viewImpl.onDidAppear)
        .onDisappear(perform: viewImpl.onDidDisappear)
    }
}

private struct NewsItemDetailsView: View {
    
    var item: NewsItem
    var onOpenClicked: () -> Void
    
    var body: some View {
        ScrollView {
            VStack(spacing: 8) {
                Text(item.title ?? "")
                    .font(/*@START_MENU_TOKEN@*/.title/*@END_MENU_TOKEN@*/)
                HStack {
                    VStack{
                        Text(item.source)
                        if (item.author != nil) {
                            Text(item.author!)
                        }
                    }
                    Spacer()
                    Text(item.publishDate)
                }
                if (item.imageUrl != nil) {
                    AsyncImage(
                        url: URL(string: item.imageUrl!)!,
                        placeholder: { ProgressView() },
                        image: { uiImage in
                            Image(uiImage: uiImage)
                                .resizable()
                        }
                    ).frame(idealHeight: 150)
                }
                if (item.content != nil) {
                    Text(item.content!)
                }
                Button("Open original source", action: onOpenClicked)
            }.padding()
        }
    }
}

private class ViewImpl : ObservableObject, NewsItemContractView {
    
    init(_presenter: NewsItemContractPresenter) {
        presenter = _presenter
        presenter.bind(view: self)
    }
    
    var presenter: NewsItemContractPresenter
    
    enum State {
        case loading
        case error(message: String)
        case loaded(item: NewsItem)
    }
    
    @Published var state: State = .loading
    
    private var item: DomainNewsItem? = nil
    
    func createRouter() -> NewsItemContractRouter {
        return RouterImpl()
    }
    
    func setNewsItem(item: DomainNewsItem) {
        self.item = item
        state = .loaded(item: NewsItem(
            id: item.hashValue,
            url: item.url,
            title: item.title,
            source: item.source.name,
            author: item.author,
            publishDate: item.publishDate.toDisplayFormat(),
            imageUrl: item.imageUrl,
            content: item.content
        ))
    }
    
    func setLoading(isLoading: Bool) {
        if (isLoading) {
            state = .loading
        }
    }
    
    func showError(error: String?) {
        state = .error(message: error ?? "Unexpected error")
    }
    
    func onDidAppear() {
        presenter.onViewVisible()
    }
    
    func onDidDisappear() {
        presenter.onViewInvisible()
    }
    
    func onOpenClicked() {
        presenter.onOpenOriginal()
    }
}

private class RouterImpl: NewsItemContractRouter {
    func toOriginal(url: String) {
        if let url = URL(string: url) {
            UIApplication.shared.open(url)
        }
    }
}

struct NewsItem : Identifiable {
    var id: Int
    var url: String
    var title: String?
    var source: String
    var author: String?
    var publishDate: String
    var imageUrl: String?
    var content: String?
}

#if DEBUG
struct NewsItemDetailView_Previews: PreviewProvider {
    static var previews: some View {
        NewsItemDetailsView(
            item: NewsItem(
                id: 1,
                url: "",
                title: "News item title",
                source: "Fox news",
                author: "Kate Berker",
                publishDate: "11/23/2021 10:43",
                imageUrl: "https://www.kindpng.com/picc/m/565-5652618_food-artwork-pusheen-drawing-cat-free-hd-image.png",
                content: "News item content"
            )) {}
    }
}
#endif
