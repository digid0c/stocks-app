package org.example.web.api.v1.model;

import lombok.Data;

import java.util.List;

@Data
public class AcquiredSharesViewModelList {

    private List<AcquiredSharesViewModel> acquiredSharesList;
}
