Copy code
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lovetolearn.SpringbootApp.entities.UserRoleEnum;

@ExtendWith(MockitoExtension.class)
class ExampleTest {

    @Mock
    private SessionFactory sessionFactory;

    @InjectMocks
    private Example example;

    private UserRoleEnum userRole;
    private String userId;
    private Integer witId;

    @BeforeEach
    void setUp() {
        userRole = UserRoleEnum.ADMIN;
        userId = "admin";
        witId = 1;
    }

    @Test
    void testGetDate_whenSessionIsNotNull() {
        // Arrange
        List<Integer> expectedList = new ArrayList<>();
        expectedList.add(1);
        Session session = mock(Session.class);
        Criteria criteria = mock(Criteria.class);
        ProjectionList projectionList = mock(ProjectionList.class);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.createCriteria(WorkflowMgmtAudit.class)).thenReturn(criteria);
        when(criteria.setProjection(any(ProjectionList.class))).thenReturn(criteria);
        when(projectionList.add(any())).thenReturn(projectionList);
        when(criteria.list()).thenReturn(expectedList);

        // Act
        List<Integer> actualList = example.getDate(userRole, userId, witId);

        // Assert
        assertEquals(expectedList, actualList);
        verify(sessionFactory, times(1)).openSession();
        verify(session, times(1)).createCriteria(WorkflowMgmtAudit.class);
        verify(criteria, times(1)).setProjection(any(ProjectionList.class));
        verify(projectionList, times(1)).add(Projections.property("WIT_ID"), "WIT_ID");
        verify(criteria, times(1)).list();
    }

    @Test
    void testGetDate_whenSessionIsNull() {
        // Arrange
        when(sessionFactory.openSession()).thenReturn(null);

        // Act
        List<Integer> actualList = example.getDate(userRole, userId, witId);

        // Assert
        assertNull(actualList);
        verify(sessionFactory, times(1)).openSession();
    }
}
