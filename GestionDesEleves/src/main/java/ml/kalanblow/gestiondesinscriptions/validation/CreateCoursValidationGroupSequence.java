package ml.kalanblow.gestiondesinscriptions.validation;


import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

@GroupSequence({Default.class, ValidationGroupOne.class, ValidationGroupTwo.class})
public interface CreateCoursValidationGroupSequence {
}
