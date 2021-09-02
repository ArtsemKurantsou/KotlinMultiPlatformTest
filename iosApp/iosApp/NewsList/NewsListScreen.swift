//
//  NewsListScreen.swift
//  iosApp
//
//  Created by Artem on 1.09.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NewsListScreen: View {
    
    init() {
        let presenter: NewsContractPresenter = koin.get(objCClass: NewsContractPresenter.self) as! NewsContractPresenter
        viewImpl = NewsViewImpl(_presenter: presenter)
    }
    
    @ObservedObject private var viewImpl: NewsViewImpl
    
    var body: some View {
        NavigationView {
            VStack {
                switch (viewImpl.state) {
                case .loading:
                    ProgressView()
                case .error(let message):
                    Text(message)
                case .loaded(let news):
                    if news.isEmpty {
                        Text("No news :(")
                    } else {
                        NewsListView(items: news, onItemSelected: { item in viewImpl.onItemSelected(selectedItem: item) })
                    }
                    
                }
            }.navigationTitle(Text("Top news"))
        }
        .onAppear(perform: viewImpl.onDidAppear)
        .onDisappear(perform: viewImpl.onDidDisappear)
    }
}


private class NewsViewImpl : ObservableObject, NewsContractView {
    
    init(_presenter: NewsContractPresenter) {
        presenter = _presenter
        presenter.bind(view: self)
    }
    
    var presenter: NewsContractPresenter
    
    enum State {
        case loading
        case error(message: String)
        case loaded(items: [ViewNewsItem])
    }
    
    @Published var state: State = .loading
    
    private var news: [DomainNewsItem] = []
    
    func createRouter() -> NewsContractRouter {
        return RouterImpl()
    }
    
    func setNews(news: [DomainNewsItem]) {
        self.news = news
        let items = news.map { item in
            ViewNewsItem(
                id: item.url.hashValue,
                url: item.url,
                title: item.title,
                source: item.source.name,
                publishDate: item.publishDate.toDisplayFormat(),
                imageUrl: item.imageUrl
            )
        }
        state = .loaded(items: items)
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
    
    func onItemSelected(selectedItem: ViewNewsItem) {
        let item = news.first { item in
            item.url == selectedItem.url
        }
        presenter.onNewsItemSelected(item: item!)
    }
}

private class RouterImpl : NewsContractRouter {
    func toNewsDetails(newsItem: DomainNewsItem) {
        (UIApplication.shared.delegate as? AppDelegate)?.navigateTo(controller: UIHostingController(rootView: NewsItemDetailScreen(newsItemId: newsItem.id)))
    }
}

#if DEBUG
struct NewsListScreen_Previews: PreviewProvider {
    static var previews: some View {
        NewsListScreen()
    }
}
#endif
