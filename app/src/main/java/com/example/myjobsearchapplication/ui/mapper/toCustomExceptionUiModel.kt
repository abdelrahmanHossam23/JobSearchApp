package com.example.myjobsearchapplication.ui.mapper

import com.example.myjobsearchapplication.domain.model.CustomExceptionDomainModel
import com.example.myjobsearchapplication.ui.model.CustomExceptionUiModel

fun CustomExceptionDomainModel.toCustomExceptionPresentationModel(): CustomExceptionUiModel {
    return when (this) {
        is CustomExceptionDomainModel.NoInternetConnectionExceptionDomainModel -> CustomExceptionUiModel.NoInternetConnection
        is CustomExceptionDomainModel.TimeoutExceptionDomainModel -> CustomExceptionUiModel.Timeout
        is CustomExceptionDomainModel.NetworkExceptionDomainModel -> CustomExceptionUiModel.Network
        is CustomExceptionDomainModel.ServiceNotFoundExceptionDomainModel, CustomExceptionDomainModel.AccessDeniedExceptionDomainModel, CustomExceptionDomainModel.ServiceUnavailableExceptionDomainModel ->
            CustomExceptionUiModel.ServiceUnreachable
        is CustomExceptionDomainModel.UnknownExceptionDomainModel -> CustomExceptionUiModel.Unknown
    }
}