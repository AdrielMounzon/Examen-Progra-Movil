package com.ucb.ucbtest.di

import android.content.Context
import com.ucb.data.BookRepository
import com.ucb.data.GithubRepository
import com.ucb.data.LoginRepository
import com.ucb.data.MovieRepository
import com.ucb.data.PushNotificationRepository
import com.ucb.data.TransactionRepository
import com.ucb.data.book.IBookLocalDataSource
import com.ucb.data.book.IBookRemoteDataSource
import com.ucb.data.datastore.ILoginDataStore
import com.ucb.data.git.IGitRemoteDataSource
import com.ucb.data.git.ILocalDataSource
import com.ucb.data.movie.IMovieRemoteDataSource
import com.ucb.data.push.IPushDataSource
import com.ucb.data.transactions.ITransactionLocalDataSource
import com.ucb.framework.book.BookLocalDataSource
import com.ucb.framework.book.BookRemoteDataSource
import com.ucb.framework.github.GithubLocalDataSource
import com.ucb.framework.github.GithubRemoteDataSource
import com.ucb.framework.movie.MovieRemoteDataSource
import com.ucb.framework.service.RetrofitBuilder
import com.ucb.ucbtest.R
import com.ucb.usecases.DoLogin
import com.ucb.usecases.FindGitAlias
import com.ucb.usecases.GetPopularMovies
import com.ucb.usecases.SaveGitalias
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.ucb.framework.datastore.LoginDataSource
import com.ucb.framework.push.FirebaseNotificationDataSource
import com.ucb.framework.transactions.TransactionLocalDataSource
import com.ucb.usecases.GetEmailKey
import com.ucb.usecases.ObtainToken
import com.ucb.usecases.RegisterIncome
import com.ucb.usecases.RegisterExpense
import com.ucb.usecases.ListTransactions
import com.ucb.usecases.DeleteTransaction
import com.ucb.usecases.GetBalance
import com.ucb.usecases.GetFavoriteBooks
import com.ucb.usecases.SaveBook
import com.ucb.usecases.SearchBooks

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providerRetrofitBuilder(@ApplicationContext context: Context) : RetrofitBuilder {
        return RetrofitBuilder(context)
    }


    @Provides
    @Singleton
    fun gitRemoteDataSource(retrofiService: RetrofitBuilder): IGitRemoteDataSource {
        return GithubRemoteDataSource(retrofiService)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(@ApplicationContext context: Context): ILocalDataSource {
        return GithubLocalDataSource(context)
    }

    @Provides
    @Singleton
    fun gitRepository(remoteDataSource: IGitRemoteDataSource, localDataSource: ILocalDataSource): GithubRepository {
        return GithubRepository(remoteDataSource, localDataSource)
    }

    @Provides
    @Singleton
    fun provideSaveGitAlias(repository: GithubRepository): SaveGitalias {
        return SaveGitalias(repository)
    }

    @Provides
    @Singleton
    fun provideGitUseCases(githubRepository: GithubRepository): FindGitAlias {
        return FindGitAlias(githubRepository)
    }

    @Provides
    @Singleton
    fun provideGetPopularMovies(movieRepository: MovieRepository, @ApplicationContext context: Context): GetPopularMovies {
        val token = context.getString(R.string.token)
        return GetPopularMovies(movieRepository, token)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(dataSource: IMovieRemoteDataSource) : MovieRepository {
        return MovieRepository(dataSource)
    }

    @Provides
    @Singleton
    fun provideMovieRemoteDataSource(retrofit: RetrofitBuilder ): IMovieRemoteDataSource {
        return MovieRemoteDataSource(retrofit)
    }

    @Provides
    @Singleton
    fun provideDoLogin(loginRepository: LoginRepository): DoLogin {
        return DoLogin(loginRepository)
    }

    @Provides
    @Singleton
    fun provideLoginRepository( loginDataSource: ILoginDataStore): LoginRepository {
        return LoginRepository(loginDataSource)
    }

    @Provides
    @Singleton
    fun provideLoginDataSource( @ApplicationContext context: Context ): ILoginDataStore {
        return LoginDataSource(context = context)
    }

    @Provides
    @Singleton
    fun provideGetEmailKey(loginRepository: LoginRepository): GetEmailKey {
        return GetEmailKey(loginRepository)
    }

    @Provides
    @Singleton
    fun provideObtainToken( pushNotificationRepository: PushNotificationRepository): ObtainToken {
        return ObtainToken(pushNotificationRepository)
    }

    @Provides
    @Singleton
    fun providePushNotificationRepository( pushDataSource: IPushDataSource): PushNotificationRepository {
        return PushNotificationRepository(pushDataSource)
    }

    @Provides
    @Singleton
    fun provideIPushDataSource(): IPushDataSource {
        return FirebaseNotificationDataSource()
    }

    @Provides
    @Singleton
    fun provideTransactionLocalDataSource(
        @ApplicationContext context: Context
    ): ITransactionLocalDataSource {
        return TransactionLocalDataSource(context)
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(
        localDataSource: ITransactionLocalDataSource
    ): TransactionRepository {
        return TransactionRepository(localDataSource)
    }

    @Provides
    @Singleton
    fun provideRegisterIncome(
        repository: TransactionRepository
    ): RegisterIncome {
        return RegisterIncome(repository)
    }

    @Provides
    @Singleton
    fun provideRegisterExpense(
        repository: TransactionRepository
    ): RegisterExpense {
        return RegisterExpense(repository)
    }

    @Provides
    @Singleton
    fun provideListTransactions(
        repository: TransactionRepository
    ): ListTransactions {
        return ListTransactions(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteTransaction(
        repository: TransactionRepository
    ): DeleteTransaction {
        return DeleteTransaction(repository)
    }

    @Provides
    @Singleton
    fun provideGetBalance(
        repository: TransactionRepository
    ): GetBalance {
        return GetBalance(repository)
    }

    @Provides
    @Singleton
    fun provideBookRemoteDataSource(retrofit: RetrofitBuilder): IBookRemoteDataSource {
        return BookRemoteDataSource(retrofit)
    }

    @Provides
    @Singleton
    fun provideBookLocalDataSource(@ApplicationContext context: Context): IBookLocalDataSource {
        return BookLocalDataSource(context)
    }

    @Provides
    @Singleton
    fun provideBookRepository(
        remoteDataSource: IBookRemoteDataSource,
        localDataSource: IBookLocalDataSource
    ): BookRepository {
        return BookRepository(remoteDataSource, localDataSource)
    }

    @Provides
    @Singleton
    fun provideSearchBooks(bookRepository: BookRepository): SearchBooks {
        return SearchBooks(bookRepository)
    }

    @Provides
    @Singleton
    fun provideSaveBook(bookRepository: BookRepository): SaveBook {
        return SaveBook(bookRepository)
    }

    @Provides
    @Singleton
    fun provideGetFavoriteBooks(repository: BookRepository): GetFavoriteBooks {
        return GetFavoriteBooks(repository)
    }

}